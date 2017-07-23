package com.rokid.soa.service.impl.manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoSocketException;
import com.rokid.soa.bo.biaozhu.Answer;
import com.rokid.soa.bo.biaozhu.AsrNlp;
import com.rokid.soa.bo.biaozhu.Chat;
import com.rokid.soa.bo.biaozhu.Question;
import com.rokid.soa.bo.biaozhu.VoiceTrigger;
import com.rokid.soa.bo.manage.Filter;
import com.rokid.soa.bo.manage.SyncTime;
import com.rokid.soa.common.Constants;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.mapper.biaozhu.AnswerMapper;
import com.rokid.soa.mapper.biaozhu.AsrNlpMapper;
import com.rokid.soa.mapper.biaozhu.ChatMapper;
import com.rokid.soa.mapper.biaozhu.QuestionMapper;
import com.rokid.soa.mapper.biaozhu.VoiceTriggerMapper;
import com.rokid.soa.mapper.manage.FilterMapper;
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
	private VoiceTriggerMapper voiceTriggerMapper;
	
	@Autowired
	private AsrNlpMapper asrNlpMapper;
	
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private ChatMapper chatMapper;
	
	@Autowired
	private AnswerMapper answerMapper;
	
	@Autowired
	private SyncTimeMapper syncTimeMapper;
	
	@Autowired
	private FilterMapper filterMapper;
	
	Connection con = null;
	Statement statement = null;
	ResultSet rs = null;
	
	//主数据连接参数
	private static String syncCount = PropertiesUtil.getConfiguration().getString("syncCount");
	
	/**
	 * 定时删除已分配但未标注的数据
	 * @throws Exception
	 */
	@Override
	public void DelWorkRec() throws Exception {
		voiceTriggerMapper.loginOutDelWorkDate(null);
		asrNlpMapper.loginOutDelWorkDate(null);
	}
	
	/**
	 * 定时删除黑名单的数据
	 * @throws Exception
	 */
	@Override
	public void DelFilterRec() throws Exception {
		voiceTriggerMapper.delFilterRec();
	}

	/*
	 * 同步语音数据
	 */
	@Override
	public ResponseMap SyncVoice() throws Exception {	
		
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		try{			
			
			System.out.println("===voice sync start ================");
			String voiceUrl = PropertiesUtil.getConfiguration().getString("voiceUrl");
			String voiceUsername = PropertiesUtil.getConfiguration().getString("voiceUsername");
			String voicePassword = PropertiesUtil.getConfiguration().getString("voicePassword");
			
			//获取同步时间
			String syncTime = getSyncTime(Constants.VOICE_TYPE);
			if(!syncTime.equals("0")){
				resMap.setFailReturn("VOICE数据存在，非首次数据导入！");
				return resMap;
			}
			
			// 连接数据库
			con = connectionMysql(voiceUrl, voiceUsername, voicePassword);
			statement = con.createStatement();
			
			boolean isFirst = true;
			
			//要执行的SQL语句                        1001   2      /sjkdf? yyyy-mmd-dd hh:mm:ss   201611111 12位   标注类型
			//首次一次同步完成
			String sql = "select id, version, path, date_created, last_updated, date, sn, status from voice_trigger";
			if(!isFirst){
				sql = sql + " where id > "+syncTime+" order by id limit " + syncCount;
			}
			
			//ResultSet类，用来存放获取的结果集！！
			rs = statement.executeQuery(sql);

			//数据编辑
			syncTime = null;
			int i= 1;
			//System.out.println("所有数据=" + );
			while(rs.next()){
				VoiceTrigger bo = new VoiceTrigger();
				//ID
				bo.setId(rs.getString("id"));
				//SN
				bo.setVersion(rs.getInt("version"));
				//SN
				bo.setSn(rs.getString("sn"));
				//创建时间
				bo.setTime(rs.getTimestamp("date_created").getTime());
				//修改时间
				bo.setUpdTime(rs.getTimestamp("last_updated").getTime());	
				//语音路径设定
				bo.setPath(rs.getString("path")); 
				//标注类型
				bo.setType(rs.getShort("status"));
				
				syncTime = bo.getId();
				
				//数据添加
				try{
					voiceTriggerMapper.insert(bo);
				}catch(DuplicateKeyException e){
					System.out.println("voice id=" + bo.getId() + " is exist!");
				}
				i++;
				
				if(i % 1000 == 0){
					System.out.println("已处理=" + i);
					if(syncTime != null) saveSyncTime(Constants.VOICE_TYPE, syncTime);
				}
			}
			
			//保存同步时间
			if(syncTime != null) saveSyncTime(Constants.VOICE_TYPE, syncTime);

			resMap.put("info","数据导入完成，本次共导入Voice数据: "+i+"件！");
			resMap.setSuccessReturn();
			System.out.println("===voice sync end ================");
			
			return resMap;
			
		}catch(Exception e){
			resMap.setFailReturn("同步错误！");
			e.printStackTrace();
			return resMap;
		}finally{
			if(rs != null){
				rs.close();				
			}
			if(statement != null){
				statement.close();				
			}
			if(con != null){
				con.close();				
			}
		}
	}
	
//	/*
//	 * 同步ASR数据
//	 */
//	@SuppressWarnings("deprecation")
//	public ResponseMap SyncAsrOld() throws Exception {	
//		
//		String server = "syslog-1";
//		
//		// 返回MAP对象
//		ResponseMap resMap = new ResponseMap();
//		Mongo mongo = null;
//		DB db = null;
//		
//		try{			
//			
//			System.out.println("===asr sync start ================");
//			
//			//获取同步时间
//			String syncTime = getSyncTime(Constants.ASR_TYPE);			
//			
//			mongo = new Mongo(server, 27017);
//			db = mongo.getDB("service");
//			DBCollection collection = db.getCollection("speech");//asr
//			
//			BasicDBObject query =new BasicDBObject();
//			query.put("time", new BasicDBObject("$gt", Double.valueOf(syncTime)));		
//			//
//			DBCursor cursor = collection.find(query).limit(Integer.valueOf(syncCount)).sort(new BasicDBObject("time",1));
//			String time = null;
//			while(cursor.hasNext()){
//				
//				DBObject one = cursor.next();
//
//				AsrNlp bo = new AsrNlp();
//				
//				//ID
//				bo.setId(one.get("_id").toString());
//				//SN
//				bo.setSn(one.get("id").toString());
//				//创建时间
//				bo.setTime(Double.valueOf(one.get("time").toString()).longValue());
//				//ASR
//				bo.setAsr(one.get("asr").toString());				
//				//DBObject nlp = (DBObject) one.get("nlp");
//				//domain 是否需要，要确认
//				bo.setDomain(one.get("domain").toString());
//				bo.setIntent(one.get("intent").toString());
//				//bo.setSlot(nlp.get("slots").toString());				
//				//语音路径设定
//				//http://syslog-1.dev.rokid-inc.com:50000/asr/20161117/010116001950-1479375360573.wav
//				String date = dateToStrS(new Date(bo.getTime()));
//				bo.setPath(date + "/" + one.get("id").toString() + "-" + bo.getTime() + ".wav"); 				
//				time = one.get("time").toString();				
//				//数据添加
//				try{
//					asrNlpMapper.insert(bo);
//				}catch(DuplicateKeyException e){
//					System.out.println(one);					
//					System.out.println("asr id=" + bo.getId() + " is exist!");
//				}catch(Exception e){
//					System.out.println(one);					
//					System.out.println("asr id=" + bo.getId() + " insert error!");
//					e.printStackTrace();
//				}
//			}
//			
//			//保存同步时间
//			if(time != null) saveSyncTime(Constants.ASR_TYPE, time);
//			
//			resMap.setSuccessReturn();
//			System.out.println("===asr sync end ================");
//			return resMap;
//
//		}catch(MongoSocketException e){
//			System.out.println("===connect mongdb error!");
//			return resMap;
//		}catch(Exception e){
//			resMap.setFailReturn("同步错误！");
//			e.printStackTrace();
//			return resMap;	
//		}finally{
//			if(mongo != null){
//				mongo.close();
//				mongo = null;
//			}
//		}
//	}
	
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
			
			System.out.println("===asr sync start ================");
			
			String filterStr = ",";
			//获取同步时间
			String syncTime = getSyncTime(6);		 //Constants.ASR_TYPE=6,583ef8052406ae1d04dc9889 12月1日开始，最小的_id
			
			mongo = new Mongo(server, 27017);
			db = mongo.getDB("service");
			DBCollection collection = db.getCollection("speech");//asr
			
			BasicDBObject query =new BasicDBObject();
			query.put("_id", new BasicDBObject("$gt", new ObjectId(syncTime)));		
			DBCursor cursor = collection.find(query).limit(Integer.valueOf(syncCount)).sort(new BasicDBObject("_id",1));//syncCount
			//query.put("time", new BasicDBObject("$gt", Double.valueOf("1.484901914891E12")));		
			//DBCursor cursor = collection.find(query).limit(Integer.valueOf(syncCount)).sort(new BasicDBObject("time",1));//syncCount
			
			String time = null;
			while(cursor.hasNext()){
				DBObject one = cursor.next();
				//System.out.println(one);
				//System.out.println(one.toString());
//				//获取黑名单
//				if(i == 0){
//					List<Filter> lst = filterMapper.list(null, null);
//					for(int x=0; x < lst.size(); x++){
//						Filter bo = lst.get(x);
//						filterStr += bo.getSn() + ",";
//					}
//				}
//				//若机器号为黑名单跳过
//				if(filterStr.indexOf(","+one.get("id").toString()+",") > 0){
//					System.out.println("id="+one.get("_id").toString() + ",sn="+one.get("id").toString()+",为黑名单，跳过数据！");
//					continue;
//				}
				
				Filter filter = filterMapper.findSn(one.get("id").toString());
				if(filter != null){
					System.out.println("id="+one.get("_id").toString() + ",sn="+one.get("id").toString()+",为黑名单，跳过数据！");
					continue;
				}

				AsrNlp bo = new AsrNlp();				
				//数据添加
				try{
					AsrNlp oldBo = asrNlpMapper.selectByPrimaryKey(one.get("_id").toString());
					if(oldBo == null){
						//ID
						bo.setId(one.get("_id").toString());
						//SN
						bo.setSn(one.get("id").toString());
						//创建时间
						bo.setTime(Double.valueOf(one.get("time").toString()).longValue());
						//ASR
						bo.setAsr(one.get("asr").toString());
						//domain 是否需要，要确认
						//DBObject nlp = (DBObject) one.get("nlp");						
						bo.setDomain(one.get("domain").toString());
						bo.setIntent(one.get("intent").toString());
						//bo.setSlot(nlp.get("slots").toString());	
						bo.setNlp(one.get("nlp").toString());	
						//语音路径设定
						//http://syslog-1.dev.rokid-inc.com:50000/asr/20161117/010116001950-1479375360573.wav
						String date = dateToStrS(new Date(bo.getTime()));
						bo.setPath(date + "/" + one.get("id").toString() + "-" + bo.getTime() + ".wav"); 									
						asrNlpMapper.insert(bo);
					}else{
						// 更新已导入数据的 domain intent
						if(StringUtils.isEmpty(oldBo.getDomain())){
							//ID
							bo.setId(one.get("_id").toString());
							//domain 是否需要，要确认
							//DBObject nlp = (DBObject) one.get("nlp");						
							bo.setDomain(one.get("domain").toString());
							bo.setIntent(one.get("intent").toString());
							//bo.setSlot(nlp.get("slots").toString());	
							bo.setNlp(one.get("nlp").toString());	
							asrNlpMapper.updateByPrimaryKeySelective(bo);
						}
					}	
					time = one.get("_id").toString();
					//System.out.println(one);
				}catch(Exception e){					
					System.out.println(one);					
					System.out.println("==================================asr _id=" + bo.getId() + " error!");
					System.out.println(e.getMessage());
				}
			}
			
			//保存同步时间
			if(time != null) saveSyncTime(6, time);
			
			resMap.setSuccessReturn();
			System.out.println("===asr sync end ================");
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
		
	/*
	 * 同步Chat话题数据
	 */
	public ResponseMap SyncChat() throws Exception {		
		
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		try{			
			
			System.out.println("===chat sync start ================");
			String chatUrl = PropertiesUtil.getConfiguration().getString("chatUrl");
			String chatUsername = PropertiesUtil.getConfiguration().getString("chatUsername");
			String chatPassword = PropertiesUtil.getConfiguration().getString("chatPassword");
			
			//获取同步时间
			String syncTime = getSyncTime(Constants.CHAT_TYPE);
			if(!syncTime.equals("0")){
				resMap.setFailReturn("CHAT数据存在，非首次数据导入！");
				return resMap;
			}
			
			// 连接数据库
			con = connectionMysql(chatUrl, chatUsername, chatPassword);
			statement = con.createStatement();
						
			//要执行的SQL语句
			boolean isFirst = true;
			//首次一次同步完成
			String sql = "select id, version, name, date_created, last_updated from chat_group";
			if(!isFirst){
				sql = sql + " where id > "+syncTime+" order by id limit " + syncCount;
			}
			
			//ResultSet类，用来存放获取的结果集！！
			rs = statement.executeQuery(sql);

			//数据编辑
			syncTime = null;
			int i = 0;
			while(rs.next()){ 
				Chat bo = new Chat();
				//ID
				bo.setId(rs.getLong("id"));
				//version
				bo.setVersion(rs.getInt("version"));
				//version
				bo.setScore(Float.valueOf("0"));
				//创建时间
				bo.setCrtTime(rs.getTimestamp("date_created").getTime());
				//更新时间
				bo.setUpdTime(rs.getTimestamp("last_updated").getTime());
				//ASR
				bo.setName(rs.getString("name"));
				
				syncTime = bo.getId().toString();
				
				//数据添加
				try{
					chatMapper.insert(bo);
				}catch(DuplicateKeyException e){
					System.out.println("chat id=" + bo.getId() + " is exist!");
				}
				
				i++;
				if(i % 1000 == 0){
					System.out.println("已处理=" + i);
					if(syncTime != null) saveSyncTime(Constants.CHAT_TYPE, syncTime);
				}
			}
			
			//保存同步时间
			if(syncTime != null) saveSyncTime(Constants.CHAT_TYPE, syncTime);
			
			resMap.put("info","数据导入完成，本次共导入Chat数据: "+i+"件！");
			resMap.setSuccessReturn();
			System.out.println("===chat sync end ================");
			return resMap;
			
		}catch(Exception e){
			resMap.setFailReturn("同步错误！");
			e.printStackTrace();
			return resMap;
		}finally{
			if(rs != null){
				rs.close();				
			}
			if(statement != null){
				statement.close();				
			}
			if(con != null){
				con.close();				
			}
		}
	}
	
	/*
	 * 同步Question数据
	 */
	public ResponseMap SyncQuestion() throws Exception {
		
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		try{			
			
			System.out.println("===question sync start ================");
			String chatUrl = PropertiesUtil.getConfiguration().getString("chatUrl");
			String chatUsername = PropertiesUtil.getConfiguration().getString("chatUsername");
			String chatPassword = PropertiesUtil.getConfiguration().getString("chatPassword");
			
			//获取同步时间
			String syncTime = getSyncTime(Constants.QUESTION_TYPE);
			if(!syncTime.equals("0")){
				resMap.setFailReturn("QUESTION数据存在，非首次数据导入！");
				return resMap;
			}
			
			// 连接数据库
			con = connectionMysql(chatUrl, chatUsername, chatPassword);
			statement = con.createStatement();
						
			//要执行的SQL语句
			boolean isFirst = true;
			//首次一次同步完成
			String sql = "select id, version, name, assign_id, chatgroup_id, date_created, last_updated from question";
			if(!isFirst){
				sql = sql + " where id > "+syncTime+" order by id limit " + syncCount;
			}
			
			//ResultSet类，用来存放获取的结果集！！
			rs = statement.executeQuery(sql);

			//数据编辑
			syncTime = null;
			int i = 0;
			while(rs.next()){
				Question bo = new Question();
				//ID
				bo.setId(rs.getLong("id"));
				//SN
				bo.setSn("");
				//version
				bo.setVersion(rs.getString("version"));
				//创建时间
				bo.setTime(rs.getTimestamp("date_created").getTime());
				//更新时间
				bo.setUpdTime(rs.getTimestamp("last_updated").getTime());
				//ASR
				bo.setTopic(rs.getString("name"));
				//assign_id
				bo.setAssignId(rs.getString("assign_id"));
				//chat_id
				bo.setChatId(rs.getLong("chatgroup_id"));
				
				syncTime = bo.getId().toString();
				
				//数据添加
				try{
					questionMapper.insert(bo);
				}catch(DuplicateKeyException e){
					System.out.println("question id=" + bo.getId() + " is exist!");
				}
				
				i++;
				if(i % 1000 == 0){
					System.out.println("已处理=" + i);
					if(syncTime != null) saveSyncTime(Constants.QUESTION_TYPE, syncTime);
				}
			}
			
			//保存同步时间
			if(syncTime != null) saveSyncTime(Constants.QUESTION_TYPE, syncTime);

			resMap.put("info","数据导入完成，本次共导入Quesion数据: "+i+"件！");
			resMap.setSuccessReturn();
			System.out.println("===question sync end ================");
			return resMap;
			
		}catch(Exception e){
			resMap.setFailReturn("同步错误！");
			e.printStackTrace();
			return resMap;
		}finally{
			if(rs != null){
				rs.close();				
			}
			if(statement != null){
				statement.close();				
			}
			if(con != null){
				con.close();				
			}
		}
	}
	
	/*
	 * 同步Answer数据
	 */
	public ResponseMap SyncAnswer() throws Exception {	
		
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		try{	
			
			System.out.println("===answer sync start ================");
			String chatUrl = PropertiesUtil.getConfiguration().getString("chatUrl");
			String chatUsername = PropertiesUtil.getConfiguration().getString("chatUsername");
			String chatPassword = PropertiesUtil.getConfiguration().getString("chatPassword");
			
			//获取同步时间
			String syncTime = getSyncTime(Constants.ANSWER_TYPE);
			if(!syncTime.equals("0")){
				resMap.setFailReturn("ANSWER数据存在，非首次数据导入！");
				return resMap;
			}
			
			// 连接数据库
			con = connectionMysql(chatUrl, chatUsername, chatPassword);
			statement = con.createStatement();
						
			//要执行的SQL语句
			boolean isFirst = true;
			//首次一次同步完成
			String sql = "select id, version, name, avatar, emotion, chatgroup_id, date_created, last_updated from answer";
			if(!isFirst){
				sql = sql + " where id > "+syncTime+" order by id limit " + syncCount;
			}
			
			
			//ResultSet类，用来存放获取的结果集！！
			rs = statement.executeQuery(sql);

			//数据编辑
			syncTime = null;
			int i = 0;
			while(rs.next()){
				Answer bo = new Answer();
				//ID
				bo.setId(rs.getLong("id"));
				//version
				bo.setVersion(rs.getLong("version"));
				//创建时间
				bo.setCrtTime(rs.getTimestamp("date_created").getTime());
				//更新时间
				bo.setUpdTime(rs.getTimestamp("last_updated").getTime());
				//ASR
				bo.setName(rs.getString("name"));
				//avatar
				bo.setAvatar(rs.getString("avatar"));
				//avatar
				bo.setEmotion(rs.getString("emotion"));
				//chat_id
				bo.setChatId(rs.getLong("chatgroup_id"));
				
				syncTime = bo.getId().toString();
				
				//数据添加
				try{
					answerMapper.insert(bo);
				}catch(DuplicateKeyException e){
					System.out.println("answer id=" + bo.getId() + " is exist!");
				}
				
				i++;
				if(i % 1000 == 0){
					System.out.println("已处理=" + i);
					if(syncTime != null) saveSyncTime(Constants.ANSWER_TYPE, syncTime);
				}
			}
			
			//保存同步时间
			if(syncTime != null) saveSyncTime(Constants.ANSWER_TYPE, syncTime);
			
			resMap.put("info","数据导入完成，本次共导入Answer数据: "+i+"件！");
			resMap.setSuccessReturn();
			System.out.println("===answer sync end ================");
			return resMap;
			
		}catch(Exception e){
			resMap.setFailReturn("同步错误！");
			e.printStackTrace();
			return resMap;
		}finally{
			if(rs != null){
				rs.close();				
			}
			if(statement != null){
				statement.close();				
			}
			if(con != null){
				con.close();				
			}
		}
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
    
    /**
	 * 获得当前连接
	 */
    public void connectionClose() throws Exception{
    	if (con != null){
    		con.close();    		
    	}
    }
    
    public static void main(String[] a){
    	System.out.println("1481967601865");
    	System.out.println(Float.valueOf("1.481967601865E12").longValue());
    	System.out.println(Double.valueOf("1.481967601865E12").longValue());
    	System.out.println(Double.valueOf("1.481967601865E12").toString());
    }

	@Override
	public ResponseMap SyncDomain() throws Exception {
		// TODO Auto-generated method stub		
		return new ResponseMap();
	}
}