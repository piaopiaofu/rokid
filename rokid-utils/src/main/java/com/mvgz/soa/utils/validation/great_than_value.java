package com.mvgz.soa.utils.validation;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 非空域时才进行验证.
 * 
 * 最小值验证
 * @author beansoft
 *
 */
public class great_than_value extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		
		BigDecimal valueBd = BigDecimal.ZERO;
		BigDecimal minValue = BigDecimal.ZERO;
		
		try {
		    minValue = BigDecimal.valueOf(Double.parseDouble(args[0]));
		} catch(Exception ex) {
		    return fieldDescription + "不是有效的数字！";
		}
		
		if(value != null && value.length() > 0 && value.trim().length() > 0) {
			try {
			    valueBd = BigDecimal.valueOf(Double.parseDouble(value));
			} catch(Exception ex) {
				return fieldDescription + "不是有效的数字";
			}
			
			if(valueBd.compareTo(minValue) <= 0) {
				return fieldDescription + "的值不能小于" + minValue + ", 当前值为" + value + "！";
			}
		}
		
		return null;
	}



}
