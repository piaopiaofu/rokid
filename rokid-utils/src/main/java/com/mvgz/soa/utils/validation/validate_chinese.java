package com.mvgz.soa.utils.validation;

/**
 * 纯汉字验证
 * @author beansoft
 *
 */
public class validate_chinese extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "只能输入汉字！";
	}

	@Override
	public String getRegEx() {
		return "^[\u4e00-\u9fa5]+$";
	}
}