package com.rokid.soa.dao.biaozhu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rokid.soa.bo.biaozhu.Nlp;
import com.rokid.soa.dao.common.CommDao;
import com.rokid.soa.mapper.biaozhu.NlpMapper;


@Repository
/**
 * NLP标注DAO
 */
public class NlpDao extends CommDao {

	@Autowired
	private NlpMapper nlpMapper;

	/**
	 * 标注一栏数量
	 * @param userName
	 * @param password
	 * @return
	 */
	public int listCount( Long startDate, Long endDate, String sn,  String asr, 
			String domain, String intent, Integer slot, Integer type) {
		return nlpMapper.listCount(startDate, endDate, sn, asr, domain, intent, slot, type);
	}
	
	/**
	 * 标注一栏
	 * @param userName
	 * @param password
	 * @return
	 */
	public List<Nlp> list(Long startDate, Long endDate, String sn,  String asr, 
			String domain, String intent, Integer slot, Integer type, Integer start, Integer pageSize) {
		return nlpMapper.list(startDate, endDate, sn, asr, domain, intent, slot, type, start, pageSize);
	}
}