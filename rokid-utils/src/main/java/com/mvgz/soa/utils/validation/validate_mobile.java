package com.mvgz.soa.utils.validation;

/**
 * 手机号验证
 * TODO 更多号段的支持?
 * @author beansoft
 *
 */
public class validate_mobile extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "只能输入正确的手机号码,如:13810390000";
	}

	@Override
	public String getRegEx() {
		return "^[0-9]{11}$";
	}
}