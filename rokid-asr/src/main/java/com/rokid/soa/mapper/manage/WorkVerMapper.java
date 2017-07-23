package com.rokid.soa.mapper.manage;

import com.rokid.soa.bo.manage.WorkVer;
import com.rokid.soa.bo.manage.WorkVerKey;

public interface WorkVerMapper {
    int deleteByPrimaryKey(WorkVerKey key);

    int insert(WorkVer record);

    int insertSelective(WorkVer record);

    WorkVer selectByPrimaryKey(WorkVerKey key);

    int updateByPrimaryKeySelective(WorkVer record);

    int updateByPrimaryKey(WorkVer record);
}