/**  
*****************************************************************************
* Copyright(C)  MORETRUST Co., Ltd. 2012                                        
*****************************************************************************
* 产品名称：MT弱联盟商业平台  
* 版本信息：1.0  
* 日期：2012-5-30  
*/
package com.mvgz.soa.utils.validation;

/**  
 * 项目名称：mtbp.client  
 * 类名称：validate_money  
 * 类描述：金钱的格式验证
 * 创建人：Wj  
 * 创建时间：2012-7-30 下午9:42:42  
 * @version 1.0
 */
public class validate_money extends BaseRegexValidator {

	@Override
	public String getMessage() {
		return "请输入正确的金钱格式（8位整数2位小数）！";
	}

	@Override
	public String getRegEx() {
		return "^[0-9]\\d{0,7}(\\.[0-9]{1,2})?$";
	}
}