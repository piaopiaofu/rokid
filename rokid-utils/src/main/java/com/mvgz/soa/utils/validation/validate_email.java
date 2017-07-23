package com.mvgz.soa.utils.validation;

/**
 * Email地址验证
 * @author beansoft
 *
 */
public class validate_email extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "只能输入有效的电子邮件地址, 例如a@b.com";
	}

	@Override
	public String getRegEx() {
		return "\\w{1,}[@][\\w\\-]{1,}([.]([\\w\\-]{1,})){1,3}$";
	}
}