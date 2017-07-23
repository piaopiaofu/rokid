package com.rokid.soa.mapper.manage;

import java.util.List;

import com.rokid.soa.bo.manage.Intent;
import com.rokid.soa.bo.manage.IntentKey;

public interface IntentMapper {
    int deleteByPrimaryKey(IntentKey key);

    int insert(Intent record);

    int insertSelective(Intent record);

    Intent selectByPrimaryKey(IntentKey key);

    int updateByPrimaryKeySelective(Intent record);

    int updateByPrimaryKey(Intent record);
    
    List<Intent> list(String domain, Integer type);
    
    int deleteSync(String ver);
}