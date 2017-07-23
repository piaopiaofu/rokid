/**  
*****************************************************************************
* Copyright(C)  MORETRUST Co., Ltd. 2012                                        
*****************************************************************************
* 产品名称：MT弱联盟商业平台  
* 版本信息：1.0  
* 日期：2012-5-30  
*/
package com.mvgz.soa.utils.validation;

import java.util.Map;

/**  
 * 项目名称：mtbp.client  
 * 类名称：BaseRegexValidator  
 * 类描述：单个表单域验证,基于正则表达式验证的基类.
 * 创建人：Wj  
 * 创建时间：2012-5-30 下午9:42:42  
 * @version 1.0
 */
public abstract class BaseRegexValidator extends BaseValidator {

	
	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		
		// 非空时方进行验证
		if(value != null && value.length() > 0) {
			if(!value.matches(getRegEx())) {
				return fieldDescription + getMessage();
			}
		}
		return null;
	}
	
	/**
	 * 返回验证消息.
	 * 子类需实现.
	 * @return 验证结果消息, 为空时表示没有错误
	 */
	public abstract String getMessage();
	
	/**
	 * 获取验证规则.
	 * 子类需实现.
	 * @return 正则表达式
	 */
	public abstract String getRegEx();

}