package com.mvgz.soa.utils.validation;

import java.util.Map;


/**
 * 身份证验证
 * @see IdcardValidator
 * @author beansoft
 *
 */
public class validate_id extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		
		if(value != null && value.length() > 0) {
			if(!new IdcardValidator().isValidatedAllIdcard(value)) {
				return fieldDescription + "的输入值不是合法的身份证号码！";
			}

		}
		return null;
	}

}