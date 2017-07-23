package com.mvgz.soa.utils.validation;

/**
 * 固定电话
 * @author beansoft
 *
 */
public class validate_telephone extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "只能输入正确的固定电话或手机号！";
	}

	@Override
	public String getRegEx() {
		return "(^(([0-9]{3})|([0-9]{4}))[-]\\d{6,8}$)|(^\\d{6,8}$)|(^[0-9]{11}$)";
	}
}