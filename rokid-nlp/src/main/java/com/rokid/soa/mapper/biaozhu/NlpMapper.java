package com.rokid.soa.mapper.biaozhu;

import java.util.List;

import com.rokid.soa.bo.biaozhu.Nlp;
import com.rokid.soa.bo.manage.Count;

public interface NlpMapper {
    int deleteByPrimaryKey(String id);

    int insert(Nlp record);

    int insertSelective(Nlp record);

    Nlp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Nlp record);

    int updateByPrimaryKey(Nlp record);
    
    /**
	 * 语音标注一栏数量
	 */
	int listCount( Long startDate, Long endDate, String sn,  String asr, 
			String domain, String intent, Integer slot, Integer type);
	
	/**
	 * 语音标注一栏
	 */
	List<Nlp> list(Long startDate, Long endDate, String sn,  String asr, 
			String domain, String intent, Integer slot, Integer type, Integer start, Integer pageSize);
	
	
	List<Count> count(String type, String domain, List<String> domainList, Long sDate, Long eDate);
}