package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 两个表单域的取值大于验证
 * @author beansoft
 *
 */
public class great_than extends BaseValidator {

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
			// 先尝试进行数字比较, 否则作为字符串比较
			boolean result = false;
			try {
				result = Double.parseDouble(value) <= Double.parseDouble(value2);
			} catch(Exception ex) {
				result = value.compareTo(value2) <= 0;
			}
			if(result) {
				if(anotherFieldDescription != null) {
					return fieldDescription + "的输入值" + value + "必须大于" + anotherFieldDescription + "的值" + value2+"！";
				} else {
					return fieldDescription + "的输入值" + value + "必须大于前面的值" + value2+"！";
				}
			}
		}
		return null;
	}

}