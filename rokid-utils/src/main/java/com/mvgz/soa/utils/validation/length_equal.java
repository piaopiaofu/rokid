package com.mvgz.soa.utils.validation;

import java.util.Map;

/**
 * 单个表单域验证
 * 
 * 非空域时才进行字符串验证.
 * 长度相等验证
 * 
 * @author beansoft
 * 
 */
public class length_equal extends BaseValidator {

    public String doValidation(String fieldName, String fieldDescription, Map<String, String[]> params, String... args) {
        super.setParams(params);
        String value = getValue(fieldName);

        int length = 0;

        try {
            length = Integer.parseInt(args[0]);
        } catch (Exception ex) {
        }

        if (value != null && value.length() > 0 && value.trim().length() > 0) {
            if (value.trim().length() != length) {
                return fieldDescription + "的长度必须为" + length + ", 当前长度为" + value.trim().length() + "！";
            }
        }

        return null;
    }

}