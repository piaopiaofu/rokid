package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 非空域时才进行验证.
 * 
 * 最小值验证
 * @author beansoft
 *
 */
public class min_value extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		
		double valueDouble = 0;
		double maxValue = 0;
		
		try {
			maxValue = Double.parseDouble(super.getExpression());// 防止负数出异常
		} catch(Exception ex) {
			return "请为" + fieldDescription + "指定有效的最小值数字！";
		}
		
		if(value != null && value.length() > 0 && value.trim().length() > 0) {
			try {
				valueDouble = Double.parseDouble(value);
			} catch(Exception ex) {
//				return fieldDescription + "不是有效的数字";
				return null;
			}
			
			if(valueDouble < maxValue) {
				return fieldDescription + "的值不能小于" + super.getExpression() + ", 当前值为" + value+"！";
			}
		}
		
		return null;
	}



}
