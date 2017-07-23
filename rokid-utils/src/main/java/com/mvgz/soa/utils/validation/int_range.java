package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 整数输入范围验证
 * @author beansoft
 *
 */
public class int_range extends BaseTwoNumberValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		args = super.parseTwoNumberArgs();//重新分析
		
		// 非空时进行验证
		if(value != null && value.length() > 0) {
			boolean result = false;
			try {
				result =  (Integer.parseInt(value) < Integer.parseInt(args[0])) ||
					(Integer.parseInt(value) > Integer.parseInt(args[1]));
			} catch(Exception ex) {
				return fieldDescription + "的输入值不是有效的整数！";
			}
			
			if(result) {
				return fieldDescription + "的输入值" + value + "应该为" + args[0] + "到" + args[1] + "之间的整数！";
			}
		}
		return null;
	}
	
}