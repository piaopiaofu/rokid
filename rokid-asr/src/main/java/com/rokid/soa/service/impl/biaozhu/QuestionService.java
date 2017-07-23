package com.rokid.soa.service.impl.biaozhu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rokid.soa.bo.biaozhu.Question;
import com.rokid.soa.bo.biaozhu.Chat;
import com.rokid.soa.bo.manage.User;
import com.rokid.soa.bo.manage.WorkUser;
import com.rokid.soa.common.BeanUtil;
import com.rokid.soa.common.Constants;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.common.RokidUtils;
import com.rokid.soa.dao.biaozhu.QuestionDao;
import com.rokid.soa.dao.biaozhu.ChatDao;
import com.rokid.soa.mapper.biaozhu.ChatMapper;
import com.rokid.soa.mapper.manage.UserMapper;
import com.rokid.soa.mapper.manage.WorkMapper;
import com.rokid.soa.mapper.manage.WorkUserMapper;
import com.rokid.soa.service.biaozhu.IQuestionService;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.service.impl.common.PropertiesUtil;
import com.rokid.soa.vo.biaozhu.QuestionVo;

/**
 * ASRNLP SERVICE Created by tong on 16/10/26.
 */
@Transactional
@Service
public class QuestionService extends CommonService implements IQuestionService {

	/** 聊天标注DAO */
	@Autowired
	private QuestionDao chatDao;
	
	/** 聊天标注FILTERDAO */
	@Autowired
	private ChatDao chatAnswerDao;
	
	/** 指派主表MAPPER  */
	@Autowired
	private WorkMapper workMapper;
	
	/** 指派用户表MAPPER  */
	@Autowired
	private WorkUserMapper workUserMapper;
	
	/** 用户表MAPPER  */
	@Autowired
	private UserMapper userMapper;
	
	/** 指派用户表MAPPER  */
	@Autowired
	private ChatMapper chatMapper;
	
	/** 指派用户表MAPPER  */
	@Autowired
	private QuestionDao questionDao;
	
	/**
	 * 标注更新
	 */
	@Override
	public ResponseMap update(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		Long id = reqMap.getLongValue("标注ID", "id", RequestMap.MUST_INPUT);
		Long chatId = reqMap.getLongValue("话题ID", "chatId", RequestMap.NOT_MUST_INPUT);
		String assignId = reqMap.getStringValue("Assign", "assignId", RequestMap.NOT_MUST_INPUT);
		// 用户ID
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);
		
		//业务处理
		try {

			if(!StringUtils.isEmpty(assignId)){
				chatId = null;
			}
			
			Question bo = new Question();
			// 聊天标注ID
			bo.setId(id);
			// Assign
			bo.setAssignId(assignId);
			bo.setChatId(chatId);
			// 标注类型
			// 标注用户
			bo.setUpdId(sessionUserId);
			// 标注时间
			bo.setUpdTime(RokidUtils.getSysTime());
			
			// 数据更新
			int ret = chatDao.updateByPrimaryKeySelective(bo);
			if(ret == 0){
				resMap.setFailReturn("数据不存在或已被删除！");
				return resMap;
			}
						
			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;
	}
	
	/**
	 * 聊天标注一栏数据查询显示
	 */
	@Override
	public ResponseMap list(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		Integer page = reqMap.getIntegerValue("页码", "page", RequestMap.NOT_MUST_INPUT);
		// 是否获取指派数据
		Integer getWork = reqMap.getIntegerValue("是否获取指派数据", "getWork", RequestMap.NOT_MUST_INPUT);
		// 用户ID
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);
		// 用户TYPE
		Short sessionUserType = reqMap.getShortValue(Constants.SESSION_USER_TYPE);

		List<QuestionVo> list= new ArrayList<QuestionVo>();
		
		//业务处理
		try {	
			//当管理员账号登录时，查询所有数据
			if(sessionUserType != null && sessionUserType.compareTo((short) 1) == 0){		
				// 用户一栏查询
				int count = questionDao.listCount(null);
				// 获取总页数
				int pageCount = RokidUtils.getPageCnt(count);
				if(page == null) page = 1;
				if(page > pageCount){
					page = pageCount;
				}
				// 开始数据下标
				int start = (page - 1) * RokidUtils.getPageSize();
				// 用户一栏查询
				List<Question> listBo = questionDao.list(null, start, RokidUtils.getPageSize());
				for(int i = 0; i < listBo.size(); i++){
					QuestionVo vo = new QuestionVo();
					Question bo = listBo.get(i);
					BeanUtil.copyProperties(vo, bo);
					
					// 查询用户
					User user = userMapper.selectByPrimaryKey(bo.getUpdId());
					if(user != null) vo.setUpdName(user.getUser());
					
					// 查询话题
					Chat chat = chatMapper.selectByPrimaryKey(bo.getChatId());
					if(chat != null) vo.setChatName(chat.getName());
					
					list.add(vo);
				}
				
				// 一栏记录返回
				resMap.put("page", page);
				resMap.put("pageCount", pageCount);
				resMap.put("pageSize", RokidUtils.getPageSize());
			}else{				
				/**
				 * 查询指派并未完成的指派数据
				 */
				if(getWork != null){
					// 指派数据是否有
					boolean workFlg = false;
					// 指派数据件数
					int ret = 0;
					// 查询指派用户表数据
					List<WorkUser> workList = workUserMapper.selectWorkByUserId(sessionUserId, Constants.CHAT_TYPE);
					for(int i =0; i < workList.size(); i++){
						WorkUser wu = workList.get(i);
						String time = wu.getTime();
	
						/**
						 * 更新指派数据
						 */
						Long startDate = null;
						Long endDate = null;
						if(time.length() == 7){
							time = time + "-01 00:00:00";
							startDate = strToDate(time).getTime();				
							endDate = DateUtils.addMonths(strToDate(time), 1).getTime() - 1;
						}else if(time.length() == 10){
							time = time + " 00:00:00";
							startDate = strToDate(time).getTime();				
							endDate = DateUtils.addDays(strToDate(time), 1).getTime() - 1;
						}
						System.out.println("work search for voice ============");
						System.out.println(dateToStrL(new Date(startDate)));
						System.out.println(dateToStrL(new Date(endDate)));
						
						ret = chatDao.getWorkRecordByWorkId(startDate, endDate, wu.getWorkId(), sessionUserId);
	
						//若获取分配数据为0，更新指派用户表，标注为已完成
						if(ret < RokidUtils.getPageSize()){
							// 更新指派为已完成
							workMapper.updateWorkEndById(Constants.CHAT_TYPE, RokidUtils.getSysTime(), wu.getWorkId());				
							// 有指派数据
							if(ret > 0){
								workFlg = true;
								break;
							}
						}
					}
				
					// 指派数据没有，获取非指派数据
					if(!workFlg){
						ret = chatDao.getWorkRecordByNormal(sessionUserId);
					}					
				}
				
				// 一栏数据查询
				List<Question> listBo = questionDao.list(sessionUserId ,null, null);
				for(int i = 0; i < listBo.size(); i++){
					QuestionVo vo = new QuestionVo();
					Question bo = listBo.get(i);
					BeanUtil.copyProperties(vo, bo);
					
					// 查询话题
					Chat chat = chatMapper.selectByPrimaryKey(bo.getChatId());
					if(chat != null) vo.setChatName(chat.getName());
					
					list.add(vo);
				}
				
				// 获取标注信息提示
				if(getWork != null){
					if(getWork == list.size()){
						resMap.put("info", "没有最新的标注数据了");
					}else{
						resMap.put("info", "本次新获取到 " + (list.size())  +" 条标注数据");
					}
				}
			}
			
			// 一栏记录返回
			resMap.put("list", list);
			
			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;		
	}

	@Override
	public ResponseMap chatfilter(RequestMap requestMap) {
		
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// topic
		String topic = requestMap.getStringValue("topic", "topic", RequestMap.MUST_INPUT);
				
		//业务处理
		try {

			//业务处理
			Process process = null;  
	        String processList = "";  
	        
	        String chatfilter = PropertiesUtil.getConfiguration().getString("chatfilter");

            process = Runtime.getRuntime().exec(chatfilter + " " + topic);  
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));  
            String line = "";  
            while ((line = input.readLine()) != null) {  
            	processList = processList + line;  
            }  
            System.out.println(processList); 
            input.close();  
            
            //processList = "[{\"strSent\":\"你好1\",\"fScore\":\"0.8958\",\"gid\":101},{\"strSent\":\"你好2\",\"fScore\":\"0.8958\",\"gid\":102}]";
            
            resMap.put("chatfilterList", processList);
            resMap.setSuccessReturn();
            
		// 业务异常
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;
	}
}