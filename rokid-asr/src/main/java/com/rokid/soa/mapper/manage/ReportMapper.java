package com.rokid.soa.mapper.manage;

import java.util.List;

import com.rokid.soa.bo.report.ActiveReport;
import com.rokid.soa.bo.report.AsrReport;
import com.rokid.soa.bo.report.AsrSexReport;
import com.rokid.soa.bo.report.ChatNewReport;
import com.rokid.soa.bo.report.ChatReport;
import com.rokid.soa.bo.report.VoiceReport;

/**
 * ASR/NLP统计Mapper
 * @author Administrator
 *
 */
public interface ReportMapper {
  	
	/** asr/nlp统计  */
	List<AsrReport> asrNlp(String dateType,Long startDate,Long endDate,String sn,String domain,String intent,String slot,
			Boolean isAsr, Boolean isSn, Boolean isDomain, Boolean isIntent, Boolean isSlot);
	
	/** asr/nlp 性别统计  */
	List<AsrSexReport> asrNlpSex(String dateType, Long sDate, Long eDate, String sn);
	
	/** active 活跃度统计  */
	List<ActiveReport> active(String dateType, Long sDate, Long eDate, String sn);
	
	/** 语音标注统计  */
	List<VoiceReport> voice(String dateType,Long startDate,Long endDate,String sn, Boolean isSn);
	
	/** 聊天标注统计  */
	List<ChatReport> chat(String dateType,Long startDate,Long endDate,String assign, Boolean isAssign, String sn, Boolean isSn);
	
	/** 聊天标注统计  */
	int chatNewCount(Long startDate,Long endDate,String assign, String sn);
	List<ChatNewReport> chatNew(Long startDate,Long endDate,String assign, String sn, Integer start, Integer size);
	
	/** 聊天标注批量删除  */
	int chatNewDel(String ids);
}