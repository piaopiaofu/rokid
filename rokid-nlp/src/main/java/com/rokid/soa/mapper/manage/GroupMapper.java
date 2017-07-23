package com.rokid.soa.mapper.manage;

import java.util.List;

import com.rokid.soa.bo.manage.Group;

public interface GroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
    
    Group selectByName(String name);
    
    List<Group> list();
}