package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 最小输入长度验证
 * @author beansoft
 *
 */
public class min_length extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		
		int length = 0;
		
		try {
			length = Integer.parseInt(args[0]);
		} catch(Exception ex) {}
		
		if(value != null && value.length() > 0 && value.trim().length() > 0) {
			if(value.trim().length() < length) {
				return fieldDescription + "的长度不能小于" + length + ", 当前长度为" + value.trim().length()+"！";
			}
		}
		
		return null;
	}



}
