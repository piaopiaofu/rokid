package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 有效数字验证
 * @author beansoft
 *
 */
public class validate_number extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		String max = "99999999.99";
		
		if(value != null && value.length() > 0) {
			try {
				double val = Double.parseDouble(value);
				if(val > Double.valueOf(max)){
					return fieldDescription + "最大值不能超过"+ max + ",请确认！";
				}
			} catch(Exception ex) {
				return fieldDescription + "不是有效的数字！";
			}
			
		}
		return null;
	}

}