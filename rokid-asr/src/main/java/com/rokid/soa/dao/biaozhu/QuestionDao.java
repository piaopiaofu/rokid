package com.rokid.soa.dao.biaozhu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rokid.soa.bo.biaozhu.Question;
import com.rokid.soa.dao.common.CommDao;
import com.rokid.soa.mapper.biaozhu.QuestionMapper;


@Repository
/**
 * 聊天标注DAO
 */
public class QuestionDao extends CommDao {

	@Autowired
	private QuestionMapper questionMapper;

	/**
	 * 聊天标注一栏数量
	 * @param userName
	 * @param password
	 * @return
	 */
	public int listCount(String userId) {
		return questionMapper.listCount(userId);
	}
	
	/**
	 * 聊天标注一栏
	 * @param userName
	 * @param password
	 * @return
	 */
	public List<Question> list(String userId, Integer start, Integer pageSize) {
		return questionMapper.list(userId, start, pageSize);
	}
	
	/**
	 * 根据话题ID查询
	 * @param chatId
	 * @return
	 */
	public List<Question> listQuestion(Long chatId){
		return questionMapper.listQuestion(chatId);
	}
	
	/**
	 * 指派数据给标注者
	 * @param startDate
	 * @param endDate
	 * @param workId
	 * @param user
	 * @return
	 */
	public int getWorkRecordByWorkId(Long startDate, Long endDate, String workId, String user){
		return questionMapper.getWorkRecordByWorkId(startDate, endDate, workId, user);
	}
	
	/**
	 * 获取正常指派数据
	 * @param user
	 * @return
	 */
	public int getWorkRecordByNormal(String user){
		return questionMapper.getWorkRecordByNormal(user);
	}
	
	/**
	 * 修改为未标注
	 * @param user
	 * @return
	 */
	public int updateNoMarkById(Long id){
		return questionMapper.updateNoMarkById(id);
	}
	
	/**
	 * 修改为未标注
	 * @param user
	 * @return
	 */
	public int updateNoMarkByChatId(Long chatId){
		return questionMapper.updateNoMarkByChatId(chatId);
	}
	
	/**
	 * 检查名称是否已存在
	 * @param user
	 * @return
	 */
	public int nameCount(String name, Long id){
		return questionMapper.nameCount(name, id);
	}
}