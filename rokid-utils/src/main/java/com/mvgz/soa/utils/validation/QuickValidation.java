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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * 
 * 项目名称：retail-utils  
 * 类名称：QuickValidation  
 * 类描述：验证辅助类
 * 创建时间：2014年10月5日 下午6:37:10  
 * @author wandou  
 * @version 1.0
 */
public class QuickValidation {

    private ThreadLocal<List<String>> local = null;

    public void createLocal() {
        local = new ThreadLocal<List<String>>();
        local.set(new ArrayList<String>());
    }

    public void clearThreadLocal() {
        if (local != null)
            local.remove();
    }

    /**
     * 分析并执行验证, 当验证失败时返回 false.
     * 
     * @param action Action 对象
     * @param validationExpression 验证规则表达式
     */
    private String parseAndValid(Map<String, String[]> action, String validationExpressions, ParameterValidator mTBPClientValidator) {
        String[] validations = validationExpressions.split(";");
        // 处理单条验证
        for (int s = 0; s < validations.length; s++) {
            if (validations[s] != null && validations.length > 0) {
                String x = validations[s];
                FieldExpressionParser parser = new FieldExpressionParser(x);
                List<IValidator> validators = parser.getValidations();
                for (IValidator validator : validators) {
                    String msg = validator.doValidation(parser.getFieldName(), parser.getFieldDescription(), action, validator.getArgs());
                    if (msg != null) {
                        return msg;
                    }
                }
            }
        }
        return null;
    }

    public String validate(Map<String, String[]> action, ParameterValidator validator) {

        if (validator != null) {
            // 验证表达式
            String validationExpressions = validator.value();

            if (validator.enabled()){
            	String msg = parseAndValid(action, validationExpressions, validator);
            	if(msg != null) return msg;
            }
        }
        return null;
    }

    public void destroy() {
    }

    public void init() {
    }

}
