package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 输入长度范围验证
 * @author beansoft
 *
 */
public class length_range extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		
		if(value != null && value.length() > 0 && value.trim().length() > 0) {
			
			boolean result = false;
			try {
				result =  (value.length() < Integer.parseInt(args[0])) ||
					(value.length() > Integer.parseInt(args[1]));
			} catch(Exception ex) {
				return fieldDescription + "的长度范围不是有效的整数！";
			}
			
			if(result) {
				return fieldDescription + "的输入值的长度应该为" + args[0] + "到" + args[1] + "之间,当前长度为" + value.length()+"！";
			}
		}
		
		return null;
	}



}
