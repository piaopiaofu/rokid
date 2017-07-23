package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 非空域, 全部空格也算空.
 * @author beansoft
 *
 */
public class required extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		
		if(value == null || value.length() == 0 || value.trim().length() == 0) {
			return fieldDescription + "不能为空！";
		}
		return null;
	}

}