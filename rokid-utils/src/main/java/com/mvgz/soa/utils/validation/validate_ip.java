package com.mvgz.soa.utils.validation;

/**
 * IP地址验证
 * TODO IPV6?
 * @author beansoft
 *
 */
public class validate_ip extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "只能输入有效的IP地址！";
	}

	@Override
	public String getRegEx() {
		return "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	}
}