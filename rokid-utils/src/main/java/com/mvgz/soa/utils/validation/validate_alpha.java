package com.mvgz.soa.utils.validation;

/**
 * 只能是字母a-z, A-Z
 * @author beansoft
 *
 */
public class validate_alpha extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "只能输入英文字母！";
	}

	@Override
	public String getRegEx() {
		return "^[a-zA-Z]+$";
	}
}