package com.rokid.soa.mapper.biaozhu;

import java.util.List;

import com.rokid.soa.bo.biaozhu.Question;

public interface QuestionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);
    
    /**
	 * 语音标注一栏数量
	 */
	int listCount(String userId);
	
	/**
	 * 语音标注一栏
	 */
	List<Question> list(String userId, Integer start, Integer pageSize);
	
	/**
	 * 根据话题ID查询
	 */
	List<Question> listQuestion(Long chatId);
	
	/**
	 * 分工指派
	 */
	int updateWorkIdByDay(Long startDate, Long endDate, String workId);
	int updateWorkIdByMonth(Long startDate, Long endDate, String workId);
	int deleteWorkIdByDay(Long startDate, Long endDate, String workId);
	int deleteWorkIdByMonth(Long startDate, Long endDate, String workId);
	
	/**
	 * 指派数据给标注者
	 * @param startDate
	 * @param endDate
	 * @param workId
	 * @param user
	 * @return
	 */
	int getWorkRecordByWorkId(Long startDate, Long endDate, String workId, String user);
	
	/**
	 * 获取正常指派数据
	 * @param user
	 * @return
	 */
	int getWorkRecordByNormal(String user);
	
	/**
	 * 修改为未标注
	 * @param id
	 * @return
	 */
	int updateNoMarkById(Long id);
	
	/**
	 * 修改为未标注
	 * @param chatId
	 * @return
	 */
	int updateNoMarkByChatId(Long chatId);
	
	/**
	 * 检查名称是否已存在
	 * @param name
	 * @param id
	 * @return
	 */
    int nameCount(String name, Long id);

}