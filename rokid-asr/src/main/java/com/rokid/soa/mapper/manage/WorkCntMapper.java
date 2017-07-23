package com.rokid.soa.mapper.manage;

import com.rokid.soa.bo.manage.WorkCnt;
import com.rokid.soa.bo.manage.WorkCntKey;

public interface WorkCntMapper {
    int deleteByPrimaryKey(WorkCntKey key);

    int insert(WorkCnt record);

    int insertSelective(WorkCnt record);

    WorkCnt selectByPrimaryKey(WorkCntKey key);

    int updateByPrimaryKeySelective(WorkCnt record);
    
    int updateByPrimaryKeySelective2(WorkCnt record);

    int updateByPrimaryKeyWithBLOBs(WorkCnt record);

    int updateByPrimaryKey(WorkCnt record);
    
    int updateEndTimeNull(WorkCnt record);
}