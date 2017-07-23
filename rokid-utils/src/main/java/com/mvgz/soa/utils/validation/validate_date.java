package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 日期验证
 * @author beansoft
 *
 */
public class validate_date extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		
		String df = getExpression();
		if(df == null) {
			df = "yyyy-MM-dd";// 默认格式
		}
		
		if(value != null && value.length() > 0) {
			try {
				new java.text.SimpleDateFormat(df).parse(value);
			} catch(Exception ex) {
				return fieldDescription + "的输入值不是有效的日期,正确格式为:" + df+"！";
			}
			
		}
		return null;
	}

}