package com.mvgz.soa.utils.validation;


/**
 * 整数验证
 * @author beansoft
 *
 */
public class validate_integer extends BaseRegexValidator {

	
	@Override
	public String getMessage() {
		return "只能输入整数！";
	}

	@Override
	public String getRegEx() {
		//return "^[-+]?[1-9]\\d*$|^0$/";
		return "^[0-9]+$";
	}

}