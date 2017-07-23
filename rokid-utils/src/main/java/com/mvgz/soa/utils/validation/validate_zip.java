package com.mvgz.soa.utils.validation;

/**
 * 邮编验证
 * @author beansoft
 *
 */
public class validate_zip extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "只能输入正确的邮编,如:100000";
	}

	@Override
	public String getRegEx() {
		return "^[0-9]\\d{5}$";
	}
}