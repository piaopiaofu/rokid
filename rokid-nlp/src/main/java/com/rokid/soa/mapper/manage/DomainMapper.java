package com.rokid.soa.mapper.manage;

import java.util.List;

import com.rokid.soa.bo.manage.Domain;

public interface DomainMapper {
    int deleteByPrimaryKey(String domain);

    int insert(Domain record);

    int insertSelective(Domain record);

    Domain selectByPrimaryKey(String domain);

    int updateByPrimaryKeySelective(Domain record);

    int updateByPrimaryKey(Domain record);
    
    List<Domain> list(Integer type, Integer groupId);
    
    int deleteSync(String ver);
}