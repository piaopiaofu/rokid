package com.rokid.soa.mapper.manage;

import java.util.List;

import com.rokid.soa.bo.manage.CountUserYm;
import com.rokid.soa.bo.manage.Work;

public interface WorkMapper {
    int deleteByPrimaryKey(String id);

    int insert(Work record);

    int insertSelective(Work record);

    Work selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Work record);

    int updateByPrimaryKey(Work record);
    
	/** 用户一栏数量  */
	int listCount();
	
	/** 一栏  */
	List<Work> list(Integer start, Integer pageSize);
	
	/** 选择用户一栏  */
	//List<IdVal> listUser(List<String> ids, Boolean isFlg);
	
	/** 统计当前日期指派类型的数量  */
	CountUserYm workCount(Long startTime, Long endTime, String id);
	
	/** 从指派中删除用户  */
	//int deleteByUserId(String userId);
	
	/** 更新指派已完成  */
	int updateWorkEndById(int type, Long date, String workId);
	
	/** 获取指派数据一栏 */
	List<Work> selectWorks(int type);
}