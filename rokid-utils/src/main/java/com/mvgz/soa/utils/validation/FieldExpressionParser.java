/**  
*****************************************************************************
* Copyright(C)  MORETRUST Co., Ltd. 2012                                        
*****************************************************************************
* 产品名称：MT弱联盟商业平台  
* 版本信息：1.0  
* 日期：2012-5-31  
*/
package com.mvgz.soa.utils.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**     
 * 项目名称：mtbp.client  
 * 类名称：FieldExpressionParser  
 * 类描述：类型验证辅助类
 * 创建人：Wj  
 * 创建时间：2012-5-31 下午1:31:06  
 * @version 1.0
 */
public class FieldExpressionParser {
	private String fieldName;
	String fieldDescription;
	String validExpression;
	static String VALIDATOR_PACKAGE = "com.mvgz.soa.utils.validation.";// 验证规则类所在包
	static Map<String, IValidator> valudators = new HashMap<String, IValidator>(); // 添加缓存

	/**
	 * username,用户名,required min-length-5
	 * 
	 * @param expression
	 */
	 public FieldExpressionParser(String expression) {
		String[] parts = expression.split(",");
		try {
			fieldName = parts[0];
			fieldDescription = parts[1];
			if (fieldDescription == null
					|| fieldDescription.trim().length() == 0) {
				fieldDescription = fieldName;
			}
			validExpression = parts[2].trim();// 避免额外的空格
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public  String getFieldDescription() {
		return fieldDescription;
	}

	/**
	 * 分析验证器
	 * 
	 * @return
	 */
	public List<IValidator> getValidations() {
		List<IValidator> lists = new ArrayList<IValidator>();

		if (validExpression != null) {
			String[] parts = validExpression.split(" ");
			for (String part : parts) {
				String argsExpression = null;// 规则表达式
				String className = null;// 规则类名
				String[] args = null;// 参数列表(可能为空)

				// 不带-号的简单规则, 一般是 required
				if (part.indexOf('-') == -1) {
					className = part;
					// equals 需要单独处理
				} else if (part.indexOf("equals") != -1) {
					className = "equals";
					argsExpression = part.substring(className.length() + 1);
				} else if (part.indexOf('-') > 0) {
					//part = part.replaceFirst("-", "_");// 类名转换为合法标识符:
														// validate-ip ==>
														// validate_ip
					// 分析有参数的规则, 排除类名
					if (part.indexOf('-') > 0) {
						className = part.substring(0, part.indexOf('-'));// min_length-5-...更多参数
						argsExpression = part.substring(className.length() + 1);
					} else {
						// 分析无参数的规则, 例如: validate-number, 没有参数
						className = part;
					}
				}

				// System.out.println("className=" + className);
				// System.out.println("argsExpression=" + argsExpression);

				if (argsExpression != null) {
					args = argsExpression.split("-");
				}

				// 类名有效时, 尝试加载
				if (className != null && className.length() > 0) {
					try {
						IValidator validator = valudators.get(VALIDATOR_PACKAGE
								+ className) == null ? (IValidator) Class
								.forName(VALIDATOR_PACKAGE + className)
								.newInstance() : valudators
								.get(VALIDATOR_PACKAGE + className);
						validator.setArgs(args);
						validator.setExpression(argsExpression);// 原始表达式
						lists.add(validator);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println("验证器实现类" + VALIDATOR_PACKAGE
								+ className + "不存在!");
					}
				}
			}
		}

		return lists;
	}

}