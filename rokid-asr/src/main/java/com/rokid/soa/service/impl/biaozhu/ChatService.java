package com.rokid.soa.service.impl.biaozhu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rokid.soa.bo.biaozhu.Answer;
import com.rokid.soa.bo.biaozhu.Chat;
import com.rokid.soa.bo.biaozhu.Question;
import com.rokid.soa.common.BeanUtil;
import com.rokid.soa.common.Constants;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.common.RokidUtils;
import com.rokid.soa.dao.biaozhu.ChatDao;
import com.rokid.soa.dao.biaozhu.QuestionDao;
import com.rokid.soa.mapper.biaozhu.AnswerMapper;
import com.rokid.soa.service.biaozhu.IChatService;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.vo.biaozhu.ChatListVo;
import com.rokid.soa.vo.biaozhu.ChatVo;

/**
 * ASRNLP SERVICE Created by tong on 16/10/26.
 */
@Transactional
@Service
public class ChatService extends CommonService implements IChatService {

	/** 聊天标注FilterDAO */
	@Autowired
	private ChatDao chatDao;
	
	/** 聊天标注QuestionDAO */
	@Autowired
	private QuestionDao questionDao;
	
	/** 聊天标注AnswerDAO */
	@Autowired
	private AnswerMapper answerMapper;
	
	/**
	 * 标注更新
	 */
	@Override
	public ResponseMap update(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		Long id = reqMap.getLongValue("ID", "id", RequestMap.NOT_MUST_INPUT);
		Float score = reqMap.getFloatValue("分数", "score", RequestMap.MUST_INPUT);
		String name = reqMap.getStringValue("话题", "name", RequestMap.MUST_INPUT);		
		List<ChatListVo> questionList = reqMap.getListByType("questionList", ChatListVo.class);
		List<ChatListVo> answerList = reqMap.getListByType("answerList", ChatListVo.class);
		
		// 用户ID
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);
		
		//业务处理
		try {

			Chat bo = new Chat();
			// 聊天标注FilterID
			bo.setId(id);
			// 话题
			bo.setName(name);
			// 分数
			bo.setScore(score);	
			// 用户
			bo.setCrtUser(sessionUserId);
			// 时间
			bo.setCrtTime(RokidUtils.getSysTime());
			
			// 检查话题是否已经存在
			int count = chatDao.nameCount(name, id);
			if(count > 0){
				resMap.setFailReturn("该话题已存在！");
				return resMap;
			}

			// 数据更新
			int ret = 0;
			if(id != null){
				ret = chatDao.updateByPrimaryKeySelective(bo);
				if(ret == 0){
					resMap.setFailReturn("数据不存在或已被删除！");
					return resMap;
				}
				
				//采用前台check
//				if(questionList != null){
//					for(int i = 0; i < questionList.size(); i++){
//						ChatListVo vo = questionList.get(i);
//						if(vo.getAct().equals("add") || vo.getAct().equals("edit")){							
//							int cnt = questionDao.nameCount(vo.getText(), vo.getId());
//							if(cnt > 0){
//								resMap.setFailReturn("句式分发["+vo.getText()+"]重复！");
//								return resMap;
//							}
//						}
//					}	
//				}				
//				if(answerList != null){
//					for(int i = 0; i < answerList.size(); i++){
//						ChatListVo vo = answerList.get(i);
//						if(vo.getAct().equals("add") || vo.getAct().equals("edit")){
//							int cnt = answerMapper.nameCount(vo.getText(), vo.getId());
//							if(cnt > 0){
//								resMap.setFailReturn("话题整理["+vo.getText()+"]重复！");
//								return resMap;
//							}
//						}
//					}
//				}
				
				// question处理
				List<ChatListVo> retQuestionList = new ArrayList<ChatListVo>(); 
				if(questionList != null){
					for(int i = 0; i < questionList.size(); i++){
						ChatListVo vo = questionList.get(i);
						if(vo.getAct().equals("del")){
							//更新数据为未标注
							questionDao.updateNoMarkById(vo.getId());
						}else if(vo.getAct().equals("add")){
							Question ques = new Question();
							ques.setTopic(vo.getText());
							ques.setChatId(Long.valueOf(id));
							ques.setUpdId(sessionUserId);
							ques.setTime(RokidUtils.getSysTime());
							ques.setUpdTime(RokidUtils.getSysTime());
							questionDao.insert(ques);
							
							vo.setAct("load");
							vo.setId(ques.getId());
							retQuestionList.add(vo);
						}else if(vo.getAct().equals("edit")){
							Question ques = new Question();
							ques.setId(vo.getId());
							ques.setTopic(vo.getText());
							ques.setUpdId(sessionUserId);
							ques.setUpdTime(RokidUtils.getSysTime());
							questionDao.updateByPrimaryKeySelective(ques);
	
							vo.setAct("load");
							retQuestionList.add(vo);
						}else{
							retQuestionList.add(vo);
						}
					}					
				}
				resMap.put("questionList", retQuestionList);
				
				// answer处理
				List<ChatListVo> retAnswerList = new ArrayList<ChatListVo>(); 
				if(answerList != null){
					for(int i = 0; i < answerList.size(); i++){
						ChatListVo vo = answerList.get(i);
						if(vo.getAct().equals("del")){						
							answerMapper.deleteByPrimaryKey(vo.getId());
						}else if(vo.getAct().equals("add")){
							Answer ans = new Answer();
							ans.setName(vo.getText());
							ans.setChatId(Long.valueOf(id));
							ans.setCrtId(sessionUserId);
							ans.setUpdId(sessionUserId);
							ans.setCrtTime(RokidUtils.getSysTime());
							ans.setUpdTime(RokidUtils.getSysTime());
							answerMapper.insert(ans);
							
							vo.setAct("load");
							vo.setId(ans.getId());
							retAnswerList.add(vo);
						}else if(vo.getAct().equals("edit")){
							Answer ans = new Answer();
							ans.setId(vo.getId());
							ans.setName(vo.getText());
							ans.setUpdId(sessionUserId);
							ans.setUpdTime(RokidUtils.getSysTime());
							answerMapper.updateByPrimaryKeySelective(ans);
	
							vo.setAct("load");
							retAnswerList.add(vo);
						}else{
							vo.setAct("load");
							retAnswerList.add(vo);
						}
					}
				}
				resMap.put("answerList", retAnswerList);
			}else{			
				
				// question处理
				List<ChatListVo> retQuestionList = new ArrayList<ChatListVo>(); 
				// answer处理
				List<ChatListVo> retAnswerList = new ArrayList<ChatListVo>(); 
				
				// ID
				ret = chatDao.insert(bo);
				if(ret == 0){
					resMap.setFailReturn("数据添加失败！");
					return resMap;
				}
				
				// 返回
				ChatVo vo = new ChatVo();
				BeanUtil.copyProperties(vo, bo);
				vo.setAnswerList(retAnswerList);
				vo.setQuestionList(retQuestionList);
				
				resMap.put("vo", vo);
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
	 * 删除话题
	 */
	@Override
	public ResponseMap del(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		Long id = reqMap.getLongValue("标注ID", "id", RequestMap.MUST_INPUT);

		//业务处理
		try {
			
			// 更新关联该话题的question,为未标注
			int ret = questionDao.updateNoMarkByChatId(id);
			
			// 物理删除关联chatid answer数据
			ret = answerMapper.deleteByChatId(id);
			
			Chat bo = new Chat();
			// 聊天标注ID
			bo.setId(id);
			// 数据更新
			ret = chatDao.deleteByPrimaryKeySelective(bo);
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
	 * 聊天标注Filter一栏数据查询显示
	 */
	@Override
	public ResponseMap list(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		Integer page = reqMap.getIntegerValue("页码", "page", RequestMap.NOT_MUST_INPUT);
		// Topic
		String name = reqMap.getStringValue("Topic", "name", RequestMap.NOT_MUST_INPUT);
		// Question
		String question = reqMap.getStringValue("Question", "question", RequestMap.NOT_MUST_INPUT);
		// Answer
		String answer = reqMap.getStringValue("Answer", "answer", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {
			List<ChatVo> list= new ArrayList<ChatVo>();
			
			Integer start = null;
			Integer pageCount = null;
					
			// 如果是filer选择画面，不分页
			if(page != null){
				// 用户一栏查询
				int count = chatDao.listCount(name, question, answer);
				// 获取总页数
				pageCount = RokidUtils.getPageCnt(count);
				if(page > pageCount){
					page = pageCount;
				}
				// 开始数据下标
				start = (page - 1) * RokidUtils.getPageSize();
			}

			// 一栏查询
			List<Chat> listBo = chatDao.list(name, question, answer, start, RokidUtils.getPageSize());
			for(int i = 0; i < listBo.size(); i++){
				ChatVo vo = new ChatVo();
				Chat bo = listBo.get(i);
				BeanUtil.copyProperties(vo, bo);
				
				if(page != null){
					//添加question列表
					List<Question> qList = questionDao.listQuestion(bo.getId());
					List<ChatListVo> qAdd = new ArrayList<ChatListVo>();
					for(int x=0; x < qList.size(); x++){
						Question bean = qList.get(x);
						ChatListVo qo = new ChatListVo();
						qo.setId(bean.getId());
						qo.setText(bean.getTopic());
						qo.setAct("load");
						qAdd.add(qo);
					}
					vo.setQuestionList(qAdd);
					
					//添加Answer列表
					List<Answer> aList = answerMapper.listAnswer(Long.valueOf(bo.getId())); 
					List<ChatListVo> aAdd = new ArrayList<ChatListVo>();
					for(int x=0; x < aList.size(); x++){
						Answer bean = aList.get(x);
						ChatListVo qo = new ChatListVo();
						qo.setId(bean.getId());
						qo.setText(bean.getName());
						qo.setAct("load");
						aAdd.add(qo);
					}
					vo.setAnswerList(aAdd);
				}
				
				list.add(vo);
			}
			
			// 一栏记录返回
			resMap.put("page", page);
			resMap.put("pageCount", pageCount);
			resMap.put("pageSize", RokidUtils.getPageSize());
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
}