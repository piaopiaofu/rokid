package com.mvgz.soa.utils.validation;

/**
 * 只能是英文字母或是数字
 * @author beansoft
 *
 */
public class validate_alphanum extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "只能输入英文字母或是数字！";
	}

	@Override
	public String getRegEx() {
		return "^[a-zA-Z0-9]+$";
	}
}