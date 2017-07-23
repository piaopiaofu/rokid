package com.mvgz.soa.utils.validation;


/**
 * 只能包含0-9a-zA-Z任意个半角英数字
 * @author beansoft
 *
 */
public class validate_engdigits extends BaseRegexValidator {

	
	@Override
	public String getMessage() {
		return "只能输入半角英文字符或大于0整数！";
	}

	@Override
	public String getRegEx() {
		return "^[0-9a-zA-Z]+$";
	}

}