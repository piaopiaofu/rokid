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

import com.rokid.soa.bo.biaozhu.AsrNlp;
import com.rokid.soa.bo.biaozhu.VoiceTrigger;
import com.rokid.soa.dao.biaozhu.AsrNlpDao;
import com.rokid.soa.dao.biaozhu.VoiceTriggerDao;
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
	
	public class MutliThread extends Thread{

		private String id = null;
		private String url = null;
		private VoiceTriggerDao voiceTriggerDao = null;
		private AsrNlpDao asrNlpDao = null;
		
		public MutliThread(String id, String url, VoiceTriggerDao voiceTriggerDao, AsrNlpDao asrNlpDao){
			this.id = id;
			this.url = url;
			this.voiceTriggerDao = voiceTriggerDao;
			this.asrNlpDao = asrNlpDao;
	    }
	    
		@Override
	    public void run(){
			checkWav(id, url, voiceTriggerDao, asrNlpDao);
	    }
	}
	
	public Integer checkWav(String id, String url, VoiceTriggerDao voiceTriggerDao, AsrNlpDao asrNlpDao){
		Integer ret = null;
		HttpURLConnection con = null;
		try {
			//设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
			HttpURLConnection.setFollowRedirects(false);
			//到 URL 所引用的远程对象的连接
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setConnectTimeout(500);
			/* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
			con.setRequestMethod("HEAD");
			//从 HTTP 响应消息获取状态码
			ret = con.getResponseCode();
		} catch (UnknownHostException e){
			ret = null;
		} catch (Exception e) {
			//System.out.println(id+":"+url+":"+e.getMessage());
			ret = null;
		} finally{
			if(con != null){
				con.disconnect();
				con = null;
			}
		}
		
		try{
			if(ret == 404 || ret == 200){
				if(voiceTriggerDao != null){
					VoiceTrigger bo = new VoiceTrigger();
					bo.setFstatus(ret);
					bo.setId(id);
					voiceTriggerDao.updateByPrimaryKeySelective(bo);
				}else if(asrNlpDao != null){
					AsrNlp bo = new AsrNlp();
					bo.setId(id);
					bo.setFstatus(ret);
					asrNlpDao.updateByPrimaryKeySelective(bo);
				}
			}
		}catch(Exception e){
			System.out.println(id+":"+url+":"+e.getMessage());
		}
		
		return ret;
	}
}
