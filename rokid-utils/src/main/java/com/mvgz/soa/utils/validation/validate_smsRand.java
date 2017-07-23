package com.mvgz.soa.utils.validation;


/**
 * 整数验证
 * @author beansoft
 *
 */
public class validate_smsRand extends BaseRegexValidator {

	
	@Override
	public String getMessage() {
		return "验证码输入错误，请重新输入！";
	}

	@Override
	public String getRegEx() {
		//return "^[-+]?[1-9]\\d*$|^0$/";
		return "^[0-9]{4}$";
	}

}