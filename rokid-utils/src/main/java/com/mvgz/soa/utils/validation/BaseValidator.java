/**  
*****************************************************************************
* Copyright(C)  MORETRUST Co., Ltd. 2012                                        
*****************************************************************************
* 产品名称：MT弱联盟商业平台  
* 版本信息：1.0  
* 日期：2012-5-30  
*/
package com.mvgz.soa.utils.validation;

import java.util.Map;

/**  
 * 项目名称：mtbp.client  
 * 类名称：BaseValidator  
 * 类描述：抽象的验证器实现, 仅实现了 setter和getter.
 * 创建人：Wj  
 * 创建时间：2012-5-30 下午9:42:42  
 * @version 1.0
 */
public abstract class BaseValidator implements IValidator {
	private String[] args;

	
	private Map<String, String[]> params;
	private String expression;
	
	@SuppressWarnings("rawtypes")
	public Map getParams() {
		return params;
	}
	
	@SuppressWarnings("unchecked")
	public void setParams(@SuppressWarnings("rawtypes") Map params) {
		this.params = params;
	}
	
	public String[] getArgs() { return args; }
	public void setArgs(String[] args) {this.args = args;}
	
	public String[] getValues(String name) {
		return params.get(name);
	}
	
	public String getValue(String name) {
		String[] values = getValues(name);
		
		if(values != null) {
			return values[0];
		}
		
		return null;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
