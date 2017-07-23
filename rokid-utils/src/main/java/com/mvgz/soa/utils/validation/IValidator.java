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

import java.util.Map;


/**
 * 
 * 项目名称：retail-utils  
 * 类名称：IValidator  
 * 类描述：所有验证器的抽象接口.
 * 创建时间：2014年10月5日 下午6:35:01  
 * @author wandou  
 * @version 1.0
 */
public interface IValidator {
    
	/**
	 * 实现验证方法.
	 * @param fieldName 字段name
	 * @param fieldDescription 出错时的字段描述信息
	 * @param params 参数列表 <String key, String[] values> 表单参数 Map
	 * @param args 参数列表
	 * @return 验证信息, 为空时表示没有任何出错信息, 通过验证
	 */
	public String doValidation(String fieldName, String fieldDescription, Map<String, String[]> params, String... args) ;
	
	/**
	 * 获取验证器本身的参数列表.
	 * @return
	 */
	public String[] getArgs();
	
	/**
	 * 设置验证器本身的参数列表
	 * @param args - 一个或者多个参数, 允许为 null
	 */
	public void setArgs(String[] args);
	
	/**
	 * 获取原始表达式.
	 * @return
	 */
	public String getExpression();
	
		/**
	 * 设置原始表达式, 用于日期, 正则等验证方式, 避免和 - 号冲突.
	 * @param Expression
	 */
	public void setExpression(String expression);	
}
