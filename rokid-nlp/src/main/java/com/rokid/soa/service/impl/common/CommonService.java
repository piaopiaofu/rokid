package com.rokid.soa.service.impl.common;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.rokid.soa.bo.biaozhu.Nlp;
import com.rokid.soa.dao.biaozhu.NlpDao;
import com.rokid.soa.service.common.ICommonService;

/**
 * 用户日志 SERVICE
 * Created by pijiu on 15/12/30.
 */
@Service
public class CommonService implements ICommonService {
		   
    public void beanDefaultValue(Object e) throws Exception{  
        Class<? extends Object> cls = e.getClass();  
        Field[] fields = cls.getDeclaredFields();  
        for(int i=0; i<fields.length; i++){  
            Field f = fields[i];  
            f.setAccessible(true);  
            if(f.get(e) == null){
            	if(f.getType().toString().equals("class java.lang.String")){
            		f.set(e, "");
            	}else if(f.getType().toString().equals("class java.lang.Integer")){
            		f.set(e, 0);
            	}else if(f.getType().toString().equals("class java.lang.Short")){
            		f.set(e, 0);
            	}else if(f.getType().toString().equals("class java.lang.Double")){
            		f.set(e, 0);
            	}else if(f.getType().toString().equals("class java.lang.Byte")){
            		f.set(e, (byte)0);
            	}else if(f.getType().toString().equals("class java.lang.Boolean")){
            		f.set(e, false);
                }else if(f.getType().toString().equals("class java.lang.Long")){
                    f.set(e, 0l);
                }else if(f.getType().toString().equals("class java.math.BigDecimal")){
                    f.set(e, new BigDecimal(0));
                }
            }

        }   
    } 
    
    public static String change(String src) {  
        if (src != null) {  
            StringBuffer sb = new StringBuffer(src);  
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));  
            return sb.toString();  
        } else {  
            return null;  
        }  
    }
    
    public String getProperty(Object bean, String fieldName) throws Exception { 
    	Object obj = bean.getClass().getMethod("get" + change(fieldName)).invoke(bean);
    	return obj == null ? "": obj.toString();
    }  
    
    public void setProperty(Object bean, String fieldName, String value) throws Exception {  
        bean.getClass().getMethod("set" + change(fieldName), String.class).invoke(bean, value);
    } 
    
    public void setProperty(Object bean, String fieldName, int value) throws Exception {  
        bean.getClass().getMethod("set" + change(fieldName), Integer.class).invoke(bean, value);
    } 
    
    public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	
	public static String dateToStrL(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	
	public static String dateToStrS(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
}
