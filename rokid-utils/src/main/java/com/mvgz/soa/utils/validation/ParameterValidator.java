/************************* 版权声明 **********************************
 * 版权所有：Copyright (c) mvgz 2015
 *
 * 工程名称： retail-service
 * 创建者： wandou  
 * 创建日期： 2014年9月29日
 * 创建记录： 创建类结构。
 *
 * ************************* 变更记录 ********************************
 * 修改者： 
 * 修改日期：
 * 修改记录：
 *
 **/
package com.mvgz.soa.utils.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 项目名称：retail-utils  
 * 类名称：ParameterValidator  
 * 类描述：建立验证规则
 * 创建时间：2014年10月5日 下午6:37:53  
 * @author wandou  
 * @version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterValidator {
	/**
	 * 验证规则的值, 编写规则为: "字段1表单名,字段1中文名,字段1规则1 字段1规则2;字段2表单名,字段2中文名,字段2规则1 字段2规则2;", 示例:
	 * username,用户名,required min_length-5;
	 * password,密码,required;
	 * password2,重复密码,required equals-password;
	 * password,密码,length_range-5-10
	 * 
	 * @return 验证器的详细值
	 */
	public String value() default ""; 
    
	/**
	 * 是否需要启用此验证框架, 默认为是.
	 * 当不启用时, 所有验证规则检查都将被忽略.
	 * @return
	 */
	public boolean enabled() default true;
}
