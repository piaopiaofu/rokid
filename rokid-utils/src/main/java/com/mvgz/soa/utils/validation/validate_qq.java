package com.mvgz.soa.utils.validation;

/**
 * QQ 号
 * @author beansoft
 *
 */
public class validate_qq extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "只能输入正确的QQ号码,如:9991483";
	}

	@Override
	public String getRegEx() {
		return "^[0-9]+$";
	}
}