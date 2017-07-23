package com.mvgz.soa.utils.validation;


/**
 * 只能包含1-9任意个数字
 * @author beansoft
 *
 */
public class validate_digits extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "只能输入大于0的整数！";
	}

	@Override
	public String getRegEx() {
	    // 只能输入大于0的整数！
		return "^[1-9][0-9]*$";
	}

}