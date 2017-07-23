package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 商品 备注校验长度
 * @author beansoft
 *
 */
public class memo_max_length extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		
		int length = 0;
		
		try {
			length = value.trim().length();
		} catch(Exception ex) {}
		
		if(value != null && value.length() > 0 && length > 0) {
			if(value.trim().length() > 100) {
				return fieldDescription + "的长度不能大于" + 100 + ", 当前长度为" + value.trim().length()+"！";
			}
		}
		
		return null;
	}

}