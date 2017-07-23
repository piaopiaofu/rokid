package com.rokid.soa.mapper.biaozhu;

import java.util.List;

import com.rokid.soa.bo.biaozhu.VoiceTrigger;

public interface VoiceTriggerMapper {
    int deleteByPrimaryKey(String id);

    int insert(VoiceTrigger record);

    int insertSelective(VoiceTrigger record);

    VoiceTrigger selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VoiceTrigger record);

    int updateByPrimaryKey(VoiceTrigger record);
    
    /**
	 * 语音标注一栏数量
	 */
	int listCount(String userId, String sn, Long startDate, Long endDate, String type);
	
	/**
	 * 语音标注一栏
	 */
	List<VoiceTrigger> list(Integer start, Integer pageSize, String userId, String sn, Long startDate, Long endDate, String type);
	
	/**
	 * 指派数据给标注者
	 * @param startDate
	 * @param endDate
	 * @param workId
	 * @param user
	 * @return
	 */
	int getWorkRecordByWorkId(Long startDate, Long endDate, String user);
	
	/**
	 * 获取正常指派数据
	 * @param user
	 * @return
	 */
	int getWorkRecordByNormal(String user);
	
	int updateNoMark(String id, String userId);
	
	/**
	 * 用户退出删除分配
	 * @param user
	 * @return
	 */
	int loginOutDelWorkDate(String user);
	
	/**
	 * 2期修改
	 * @param id
	 * @param updId
	 * @param updTime
	 * @param editAdmin
	 * @param extField
	 * @param type
	 * @return
	 */
	int biaozhu(String id, String updId, String updTime, String editAdmin, String extField, String type);
	
	/**
	 * 删除黑名单
	 * @return
	 */
	int delFilterRec();
}