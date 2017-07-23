package com.mvgz.soa.utils.validation;

/**  
 * 项目名称：mtbp.client  
 * 类名称：BaseTwoNumberValidator  
 * 类描述：用来进行两个数字的比较的基类.
 * 创建人：Wj  
 * 创建时间：2012-5-30 下午9:42:42  
 * @version 1.0
 */
public abstract class BaseTwoNumberValidator extends BaseValidator {

	/**
	 * 重新分析数字范围的参数, 主要考虑了负数的问题.
	 * -1--2的表达式返回两个参数:-1,-2
	 * @return 解析后的数字数组
	 */
	public String[] parseTwoNumberArgs() {
		String expression = super.getExpression();
		String[] args = new String[2];
		// 如果开始符号是负数
		if(expression.charAt(0) == '-') {
			args[0] = expression.substring(0, expression.indexOf('-', 1));//-1- --> -1
			args[1] = expression.substring(expression.indexOf('-', 1) + 1);//--2 --> -2, -2 --> 2
		} else {
			args[0] = expression.substring(0, expression.indexOf('-'));
			args[1] = expression.substring(expression.indexOf('-') + 1);
		}
		
		return args;
	}

}
