package com.mvgz.soa.utils.validation;


/**
 * 基于正则表达式验证.
 * @author beansoft
 *
 */
public class validate_pattern extends BaseRegexValidator {
	
	/**
	 * 返回验证消息.
	 * 子类需实现.
	 * @return
	 */
	public String getMessage() {
		return "的输入值不匹配给定的正则表达式:" + getRegEx()+"！";
	}
	
	/**
	 * 获取验证规则.
	 * 子类需实现.
	 * @return
	 */
	public String getRegEx() {
		return super.getExpression();
	}

}