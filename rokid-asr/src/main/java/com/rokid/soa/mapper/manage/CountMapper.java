package com.rokid.soa.mapper.manage;

import com.rokid.soa.bo.manage.CountUserYm;

/**
 * 统计Mapper
 * @author Administrator
 *
 */
public interface CountMapper {
  	
	/** 用户一栏  */
	CountUserYm userYm(String userId, Long startDate, Long endDate);
}