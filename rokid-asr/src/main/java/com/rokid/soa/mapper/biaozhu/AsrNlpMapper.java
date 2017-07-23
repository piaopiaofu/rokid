package com.rokid.soa.mapper.biaozhu;

import java.util.List;

import com.rokid.soa.bo.biaozhu.AsrNlp;

public interface AsrNlpMapper {
    int deleteByPrimaryKey(String id);

    int insert(AsrNlp record);

    int insertSelective(AsrNlp record);

    AsrNlp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AsrNlp record);

    int updateByPrimaryKey(AsrNlp record);
    
    /**
	 * 语音标注一栏数量
	 */
	int listCount(String userId, String sn, Long startDate, Long endDate, Integer asrLen, String type,  String asrType);
	
	/**
	 * 语音标注一栏
	 */
	List<AsrNlp> list(Integer start, Integer pageSize, String userId, String sn, Long startDate, Long endDate, Integer asrLen, String type,  String asrType);
	
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
	 * @param asrtype
	 * @param asrEdit
	 * @param asrEditCnt
	 * @param type
	 * @return
	 */
	int biaozhu(String id, String updId, String updTime, String editAdmin, String extField, Short asrtype, String asrEdit, Short asrEditCnt, Short type);
}