//Copyright (c) 2011-2012 by Hanghang Science and Technology Co., Ltd, All rights reserved.
//Xihu Technology Park No.206 Zhenhua Rd., Hangzhou 310031 China
//
//This software is the confidential and proprietary information of 
//hzhanghang.com, Inc. ("Confidential Information"). You shall not disclose 
//such Confidential Information and shall use it only in accordance 
//with the terms of the license agreement you entered into with hzhanghang.com, Inc.
package com.rokid.soa.common;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-8-10
 * Time: 下午4:51
 * To change this template use File | Settings | File Templates.
 */
public class BeanUtil {

    public static String copyProperties(Object target, Object orgi){
        try {
            PropertyUtils.copyProperties(target, orgi);
        } catch (IllegalAccessException e) {
            return "bean 拷贝错误";
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            return "调用目标错误";
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            return "没有这个方法";
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
