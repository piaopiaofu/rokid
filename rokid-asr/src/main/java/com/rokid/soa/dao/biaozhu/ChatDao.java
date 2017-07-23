package com.rokid.soa.dao.biaozhu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rokid.soa.bo.biaozhu.Chat;
import com.rokid.soa.dao.common.CommDao;
import com.rokid.soa.mapper.biaozhu.ChatMapper;

@Repository
/**
 * 聊天标注DAO
 */
public class ChatDao extends CommDao {

	@Autowired
	private ChatMapper chatFilterMapper;
	
	/**
	 * 检查话题是否已存在
	 * @param userName
	 * @param password
	 * @return
	 */
	public int nameCount(String name, Long id) {
		return chatFilterMapper.nameCount(name, id);
	}
	
	/**
	 * 聊天标注一栏数量
	 * @param userName
	 * @param password
	 * @return
	 */
	public int listCount(String name, String question, String answer) {
		return chatFilterMapper.listCount(name, question, answer);
	}
	
	/**
	 * 聊天标注一栏
	 * @param userName
	 * @param password
	 * @return
	 */
	public List<Chat> list(String name, String question, String answer, Integer start, Integer pageSize) {
		return chatFilterMapper.list(name, question, answer, start, pageSize);
	}
}