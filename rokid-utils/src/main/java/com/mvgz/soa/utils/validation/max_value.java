package com.mvgz.soa.utils.validation;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 非空域时才进行验证.
 * 
 * 最大值验证
 * 
 * @author beansoft
 * 
 */
public class max_value extends BaseValidator {

    public String doValidation(String fieldName, String fieldDescription, Map<String, String[]> params, String... args) {
        super.setParams(params);
        String value = getValue(fieldName);

        BigDecimal valueBigDecimal = null;
        BigDecimal maxValue = null;

        try {
            maxValue = new BigDecimal(super.getExpression());// 防止负数出异常
        } catch (Exception ex) {
            return "请为" + fieldDescription + "指定有效的最大值数字！";
        }

        if (value != null && value.length() > 0 && value.trim().length() > 0) {
            try {
                valueBigDecimal = new BigDecimal(value);
            } catch (Exception ex) {
                //				return fieldDescription + "不是有效的数字";
                return null;
            }

            if (valueBigDecimal.compareTo(maxValue) > 0) {
                return fieldDescription + "的值不能大于" + super.getExpression() + ", 当前值为" + value + "！";
            }
        }

        return null;
    }

}
