package com.rokid.soa.mapper.manage;

import java.util.List;

import com.rokid.soa.bo.manage.WorkUser;

public interface WorkUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkUser record);

    int insertSelective(WorkUser record);

    WorkUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkUser record);

    int updateByPrimaryKey(WorkUser record);
    
    int deleteByWorkId(String workId);
    
    int deleteByUserId(String userId);
    
    /*查询用户指派记录*/
    List<WorkUser> selectWorkByUserId(String user, int type);
}