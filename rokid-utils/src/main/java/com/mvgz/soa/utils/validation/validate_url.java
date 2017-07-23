package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 只能是有效网址
 * TODO 是否改用正则进行验证?
 * @author beansoft
 *
 */
public class validate_url extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		
		if(value != null && value.length() > 0) {
			try {
				new java.net.URL(value);
			} catch(Exception ex) {
				return fieldDescription + "不是有效的网址！";
			}
			
		}
		return null;
	}

}