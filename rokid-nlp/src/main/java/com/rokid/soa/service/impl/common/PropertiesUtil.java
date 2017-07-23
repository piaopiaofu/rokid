/**
 * ****************************************************************************
 * Copyright(C)  MORETRUST Co., Ltd. 2012
 * ****************************************************************************
 * 产品名称：MT弱联盟商业平台
 * 版本信息：1.0
 * 日期：2012-6-14
 */
package com.rokid.soa.service.impl.common;


import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 项目名称：mtbp.common  
 * 类名称：PropertiesUtil  
 * 类描述：Properties文件操作类
 * 创建人：Wj  
 * 创建时间：2012-6-14 下午4:59:10  
 * @version 1.0
 */
public class PropertiesUtil {

    private static final Log log = LogFactory.getLog(PropertiesUtil.class);

    /**
     * 获取messages.properties里的key对应的value值
     */
    public static Configuration getConfiguration() {
        CompositeConfiguration comp_config = new CompositeConfiguration();
        // 加载xml文件
        //comp_config.addConfiguration(new XMLConfiguration("config.xml"));  
        try {
            comp_config.addConfiguration(new PropertiesConfiguration("config.properties"));

        } catch (ConfigurationException e) {
            log.warn("读取配置文件出错！", e);
            e.printStackTrace();
        }
        return comp_config;
    }

}
      
