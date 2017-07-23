package com.rokid.soa.service.impl.manage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.ibatis.type.NStringTypeHandler;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoSocketException;
import com.mongodb.util.JSON;
import com.rokid.soa.bo.biaozhu.Nlp;
import com.rokid.soa.bo.manage.Domain;
import com.rokid.soa.bo.manage.Intent;
import com.rokid.soa.bo.manage.IntentKey;
import com.rokid.soa.bo.manage.SyncTime;
import com.rokid.soa.common.Constants;
import com.rokid.soa.common.JsonUtil;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.mapper.biaozhu.NlpMapper;
import com.rokid.soa.mapper.manage.DomainMapper;
import com.rokid.soa.mapper.manage.IntentMapper;
import com.rokid.soa.mapper.manage.SyncTimeMapper;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.service.impl.common.PropertiesUtil;
import com.rokid.soa.service.manage.ISyncService;

/**
 * 数据同步服务 Created by tong on 16/11/17.
 */
@Transactional
@Service
public class SyncService extends CommonService implements ISyncService {

	
	@Autowired
	private NlpMapper nlpMapper;
	
	@Autowired
	private SyncTimeMapper syncTimeMapper;
	
	@Autowired
	private DomainMapper domainMapper;
	
	@Autowired
	private IntentMapper intentMapper;

	Connection con = null;
	Statement statement = null;
	ResultSet rs = null;
	
	//主数据连接参数
	private static String syncCount = PropertiesUtil.getConfiguration().getString("syncCount");

	/*
	 * 同步ASR数据
	 */
	@SuppressWarnings("deprecation")
	public ResponseMap SyncAsr() throws Exception {	
		
		String server = "syslog-1";
		
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		Mongo mongo = null;
		DB db = null;
		
		try{			
			
			System.out.println("===nlp sync start ================");
			
			String filterStr = ",";
			//获取同步时间
			String syncTime = getSyncTime(7);		 //Constants.ASR_TYPE=6,583ef8052406ae1d04dc9889 12月1日开始，最小的_id
			
			mongo = new Mongo(server, 27017);
			db = mongo.getDB("service");
			DBCollection collection = db.getCollection("speech");//nlp
			
			BasicDBObject query =new BasicDBObject();
			query.put("_id", new BasicDBObject("$gt", new ObjectId(syncTime)));		
			DBCursor cursor = collection.find(query).limit(Integer.valueOf(syncCount)).sort(new BasicDBObject("_id",1));//syncCount
			
			String time = null;
			while(cursor.hasNext()){
				DBObject one = cursor.next();
				
//				Filter filter = filterMapper.findSn(one.get("id").toString());
//				if(filter != null){
//					System.out.println("id="+one.get("_id").toString() + ",sn="+one.get("id").toString()+",为黑名单，跳过数据！");
//					continue;
//				}

				Nlp bo = new Nlp();				
				//数据添加
				try{
					Nlp oldBo = nlpMapper.selectByPrimaryKey(one.get("_id").toString());
					if(oldBo == null){
						//ID
						bo.setId(one.get("_id").toString());
						//type
						bo.setType((short) 0);
						//SN
						bo.setSn(one.get("id").toString());
						//创建时间
						bo.setTime(Double.valueOf(one.get("time").toString()).longValue());
						//ASR
						bo.setAsr(one.get("asr").toString());
						//domain		
						bo.setDomain(one.get("domain").toString());
						bo.setIntent(one.get("intent").toString());
						//slot
						Object nlp = one.get("nlp");
						String slot = "";
						if(nlp != null){
							String nlpStr = nlp.toString();
							bo.setNlp(nlpStr);
							if(nlp != null){
								if(!nlpStr.equals("") || nlpStr.substring(0,1).equals("{")){
									try {
										JsonNode jsonNode = JsonUtil.complexJson(nlpStr);
										jsonNode = jsonNode.get("slots");
										if(jsonNode != null){
											slot = jsonNode.toString();
											if(slot != null && !slot.equals("") && slot.substring(0,1).equals("{")){
												slot = slot.substring(1, slot.length() - 1);
											}
										}
									} catch (IOException e) {
										System.out.println("date error ========================================print under");
										System.out.println(one.toString());
										System.out.print(e.getMessage());
									}
								}
							}
						}
						bo.setSlot(slot);
						//path
						String date = dateToStrS(new Date(bo.getTime()));
						bo.setPath(date + "/" + one.get("id").toString() + "-" + bo.getTime() + ".wav"); 	
						nlpMapper.insert(bo);
					}
					time = one.get("_id").toString();
					//System.out.println(one);
				}catch(Exception e){					
					System.out.println(one);					
					System.out.println("==================================nlp _id=" + bo.getId() + " error!");
					System.out.println(e.getMessage());
				}
			}
			
			//保存同步时间
			if(time != null) saveSyncTime(7, time);
			
			resMap.setSuccessReturn();
			System.out.println("===nlp sync end ================");
			return resMap;

		}catch(MongoSocketException e){
			System.out.println("===connect mongdb error!");
			return resMap;
		}catch(Exception e){
			resMap.setFailReturn("同步错误！");
			e.printStackTrace();
			return resMap;	
		}finally{
			if(mongo != null){
				mongo.close();
				mongo = null;
			}
		}
	}
	
	public static void main(String[] a){
		//Object nlp = "{\"domain\":\"com.rokid.music1\",\"intent\":\"play_random\",\"slots\":{\"iwant\":\"要\",\"keyword\":\"音乐\",\"play\":\"听\"},\"posStart\":1,\"posEnd\":5,\"confidence\":0.89100003,\"pattern\":\"($iwant)?$play($some)?$keyword\",\"version\":\"com.rokid.music1:0.12.4\"}";
		//Object nlp = "{\"domain\":\"com.rokid.music1\",\"intent\":\"play_random\",\"slots\":{},\"posStart\":1,\"posEnd\":5,\"confidence\":0.89100003,\"pattern\":\"($iwant)?$play($some)?$keyword\",\"version\":\"com.rokid.music1:0.12.4\"}";
		Object nlp = "{\"domain\":\"com.rokid.music1\",\"intent\":\"play_random\",\"slots\":\"string test\",\"posStart\":1,\"posEnd\":5,\"confidence\":0.89100003,\"pattern\":\"($iwant)?$play($some)?$keyword\",\"version\":\"com.rokid.music1:0.12.4\"}";
		//Object nlp = "{\"domain\":\"com.rokid.music1\",\"intent\":\"play_random\",\"posStart\":1,\"posEnd\":5,\"confidence\":0.89100003,\"pattern\":\"($iwant)?$play($some)?$keyword\",\"version\":\"com.rokid.music1:0.12.4\"}";
		//Object nlp = null;
		String slot = "";
		if(nlp != null){
			String nlpStr = nlp.toString();
			//bo.setNlp(nlpStr);
			if(nlp != null){
				if(!nlpStr.equals("") || nlpStr.substring(0,1).equals("{")){
					try {
						JsonNode jsonNode = JsonUtil.complexJson(nlpStr);
						jsonNode = jsonNode.get("slots");
						if(jsonNode != null){
							slot = jsonNode.toString();
							if(slot != null && !slot.equals("") && slot.substring(0,1).equals("{")){
								slot = slot.substring(1, slot.length() - 1);
							}
						}
					} catch (IOException e) {
						//System.out.println(one.toString());
						System.out.print(e.getMessage());
					}
				}
			}
		}
		slot = slot;
	}
	
	/*
	 * 获取同步时间
	 */
	public String getSyncTime(int type){
		SyncTime syncTime = syncTimeMapper.selectByPrimaryKey(type);
		String time = "0";
		if(syncTime == null){
			syncTime = new SyncTime();			
			syncTime.setId(type);
			syncTime.setTime(time);			
			syncTimeMapper.insert(syncTime);
		}else{
			time = syncTime.getTime();
		}
		
		System.out.println("sync time start = " + time);
		return time;
	}
	
	/*
	 * 保存最新同步时间
	 */
	public void saveSyncTime(int type, String time){

		SyncTime syncTime = new SyncTime();
			
		syncTime.setId(type);
		syncTime.setTime(time);

		System.out.println("save sync time = " + time);
		syncTimeMapper.updateByPrimaryKeySelective(syncTime);
	}
	
    /**
	 * 获得当前连接
	 */
    public Connection connectionMysql(String url, String user, String password) throws Exception{

		//驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		//加载驱动程序
		Class.forName(driver);
		//getConnection()方法，连接MySQL数据库！！
		con = DriverManager.getConnection(url,user,password);
		
		return con;
    }
    
    
    public ResponseMap SyncDomain() throws Exception{
    	
    	// 返回MAP对象
    	ResponseMap resMap = new ResponseMap();
    			
    	String domainUrl = PropertiesUtil.getConfiguration().getString("domainUrl");
		String domainUsername = PropertiesUtil.getConfiguration().getString("domainUsername");
		String domainPassword = PropertiesUtil.getConfiguration().getString("domainPassword");
		
    	// 连接数据库
		con = connectionMysql(domainUrl, domainUsername, domainPassword);
		statement = con.createStatement();
    				
    	String sql = "";
        sql += "SELECT DISTINCT a.domain, i.intent ";
	    sql += " FROM rokid_intent i , rokid_app_detail d, rokid_app a ";
	    sql += "WHERE i.app_detail_id = d.app_detail_id ";
	    sql += "  AND a.app_id = d.app_id ";
	    sql += "ORDER BY domain ";
	    
	    //ResultSet类，用来存放获取的结果集！！
		rs = statement.executeQuery(sql);
		
		String ver = String.valueOf(new Date().getTime());

		//数据编辑
		int i= 1;
		//System.out.println("所有数据=" + );
		while(rs.next()){
			IntentKey key = new IntentKey();
			key.setDomain(rs.getString("domain"));
			key.setIntent(rs.getString("intent"));
			Intent intent = intentMapper.selectByPrimaryKey(key);
			if(intent == null){
				intent = new Intent();
				intent.setDomain(key.getDomain());
				intent.setIntent(key.getIntent());
				intent.setType(1);
				intent.setVer(ver);
				intentMapper.insertSelective(intent);
			}else{
				intent.setVer(ver);
				intent.setType(1);
				intentMapper.updateByPrimaryKeySelective(intent);
			}
			
			Domain domain = domainMapper.selectByPrimaryKey(key.getDomain());
			if(domain == null){
				domain = new Domain();
				domain.setDomain(key.getDomain());
				domain.setType(1);
				domain.setVer(ver);
				domainMapper.insert(domain);
			}else{	
				if(domain.getVer() == null || !domain.getVer().equals(ver)){
					domain.setVer(ver);
					domain.setType(1);
					domainMapper.updateByPrimaryKeySelective(domain);
				}
			}
		}
		
		domainMapper.deleteSync(ver);
		intentMapper.deleteSync(ver);
		
		resMap.setSuccessReturn();
		return resMap;
    }
    
    /**
	 * 获得当前连接
	 */
    public void connectionClose() throws Exception{
    	if (con != null){
    		con.close();    		
    	}
    }
    
//    public static void main(String[] a){
//    	System.out.println("1481967601865");
//    	System.out.println(Float.valueOf("1.481967601865E12").longValue());
//    	System.out.println(Double.valueOf("1.481967601865E12").longValue());
//    	System.out.println(Double.valueOf("1.481967601865E12").toString());
//    }

	@Override
	public void DelWorkRec() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DelFilterRec() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResponseMap SyncVoice() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseMap SyncChat() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseMap SyncQuestion() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseMap SyncAnswer() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}