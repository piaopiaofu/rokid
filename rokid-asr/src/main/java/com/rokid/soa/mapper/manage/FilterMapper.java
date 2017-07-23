package com.rokid.soa.mapper.manage;

import java.util.List;

import com.rokid.soa.bo.manage.Filter;

public interface FilterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Filter record);

    int insertSelective(Filter record);

    Filter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Filter record);

    int updateByPrimaryKey(Filter record);
    
    int listCount();
    List<Filter> list(Integer start, Integer pageSize);
    
    Filter findSn(String sn);
}