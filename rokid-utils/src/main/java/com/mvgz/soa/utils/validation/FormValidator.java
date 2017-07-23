package com.mvgz.soa.utils.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证器标签，用来在需要验证的Struts 2 Action 方法上标识验证信息.
 * 
 * 
 * @author 刘长炯
 * @date 2009-12-19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormValidator {

	/**
	 * 验证规则的值, 编写规则为: "字段1表单名,字段1中文名,字段1规则1 字段1规则2;字段2表单名,字段2中文名,字段2规则1 字段2规则2;", 示例:
	 * username,用户名,required min-length-5;password,密码,required;password2,重复密码,required equals-password
	 * 
	 * @return 验证器的详细值
	 */
	public String value() default "";
	
	/**
	 * 出错时的结果显示页面, 一般指向Struts2的input结果页.
	 * @return
	 */
	public String input() default "";
	
	/**
	 * 是否需要启用此验证框架, 默认为是.
	 * 当不启用时, 所有验证规则检查都将被忽略.
	 * @return
	 */
	public boolean enabled() default true;
}