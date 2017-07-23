package com.rokid.soa.mapper.manage;

import com.rokid.soa.bo.manage.SyncTime;

public interface SyncTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SyncTime record);

    int insertSelective(SyncTime record);

    SyncTime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SyncTime record);

    int updateByPrimaryKey(SyncTime record);
}