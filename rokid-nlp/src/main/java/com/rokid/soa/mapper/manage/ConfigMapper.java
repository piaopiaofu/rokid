package com.rokid.soa.mapper.manage;

import java.util.List;

import com.rokid.soa.bo.manage.Config;

public interface ConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(Config record);

    int insertSelective(Config record);

    Config selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);
    
    List<Config> list();
}