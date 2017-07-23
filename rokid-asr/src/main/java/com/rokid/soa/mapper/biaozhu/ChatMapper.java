package com.rokid.soa.mapper.biaozhu;

import java.util.List;

import com.rokid.soa.bo.biaozhu.Chat;

public interface ChatMapper {
    int deleteByPrimaryKey(String id);

    int insert(Chat record);

    int insertSelective(Chat record);

    Chat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Chat record);

    int updateByPrimaryKey(Chat record);
    
    /**
     * 检查话题是否已存在
     */
	int nameCount(String name, Long id);
    
    /**
	 * Filter一栏数量
	 */
	int listCount(String name, String question, String answer);
	
	/**
	 * Filter一栏
	 */
	List<Chat> list(String name, String question, String answer, Integer start, Integer pageSize);
}