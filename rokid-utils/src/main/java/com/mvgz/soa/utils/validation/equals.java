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
 * 类名称：equals  
 * 类描述：必须和某个input field相等,用于密码两次输入验证 
 * 创建人：Wj  
 * 创建时间：2012-5-30 下午9:42:42  
 * @version 1.0
 */
public class equals extends BaseValidator {

	public String doValidation(String fieldName, String fieldDescription,
			Map<String, String[]> params, String... args) {
		super.setParams(params);		
		String value = getValue(fieldName);
		String value2 = getValue(args[0]);
		String anotherFieldDescription = null;
		
		// 另一元素描述下标为1
		if(args.length > 1) {
			anotherFieldDescription = args[1];
		}
		
		// 非空时进行验证
		if(value != null && value.length() > 0 && value2 != null &&value2.length() > 0 ) {
			if(!value.equals(value2)) {
				if(anotherFieldDescription != null) {
					return fieldDescription + "和" + anotherFieldDescription + "的输入不一致";
				} else {
					return fieldDescription + "和前面的输入不一致";
				}
			}
		}
		return null;
	}

}