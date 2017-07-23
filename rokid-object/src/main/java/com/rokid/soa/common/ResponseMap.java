/************************* 版权声明 **********************************
 * 版权所有：Copyright (c) mvgz 2015  
 *
 * 工程名称： retail-core
 * 创建者： guanhuangbai  
 * 创建日期： 2014年9月4日
 * 创建记录： 创建类结构。
 *
 * ************************* 变更记录 ********************************
 * 修改者： 
 * 修改日期：
 * 修改记录：
 *
 **/

package com.rokid.soa.common;

import java.io.Serializable;
import java.util.HashMap;

import com.rokid.soa.common.Constants;

/** 
 * 
 * @author Administrator
 *
 */
public class ResponseMap extends HashMap<String, Object> implements Serializable {
	
    private static final long serialVersionUID = -2168621342070842380L;
    
    public static final String RETURN = "return";//返回消息标识
    
    public static final String MESSAGE = "message";//返回错误码
    
    /**
     * setSuccessReturn(设置返回正常结果)   
     * @param   name  参数描述  
     * @return  name  返回值描述     
     * @Exception 异常对象
     */
    public void setSuccessReturn() {
        this.put(RETURN, Constants.SUCCESS);
        this.put(MESSAGE, null);
    }
    
    /**
     * setFailReturn(设置返回异常信息)   
     * @param   name  参数描述  
     * @return  name  返回值描述     
     * @Exception 异常对象
     */
    public void setFailReturn(String message) {
        this.put(RETURN, Constants.FAIL);
        this.put(MESSAGE, message);
    }
}
