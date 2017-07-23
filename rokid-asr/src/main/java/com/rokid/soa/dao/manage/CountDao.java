package com.rokid.soa.dao.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rokid.soa.bo.manage.CountUserYm;
import com.rokid.soa.mapper.manage.CountMapper;


@Repository
/**
 * 统计DAO
 */
public class CountDao {

	@Autowired
	private CountMapper countMapper;

	/**
	 * 用户年月统计
	 * @return
	 */
	public CountUserYm userYm(String userId, Long startDate, Long endDate) {
		return countMapper.userYm(userId, startDate, endDate);
	}
}