package com.rokid.soa.service.impl.manage;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rokid.soa.bo.report.ActiveReport;
import com.rokid.soa.bo.report.AsrReport;
import com.rokid.soa.bo.report.AsrSexReport;
import com.rokid.soa.bo.report.ChatNewReport;
import com.rokid.soa.bo.report.ChatReport;
import com.rokid.soa.bo.report.VoiceReport;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.common.RokidUtils;
import com.rokid.soa.mapper.manage.ReportMapper;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.service.manage.IReportService;

/**
 * 统计SERVICE Created by tong on 16/10/28.
 */
@Transactional
@Service
public class ReportService extends CommonService implements IReportService {

	/** 统计DAO */
	@Autowired
	private ReportMapper reportMapper;
	
//	@Override
//	public ResponseMap init(RequestMap reqMap) {
//		// 返回MAP对象
//		ResponseMap resMap = new ResponseMap();
//
//		//业务处理
//		try {			
//					
//			Long sDate = null;
//			Long eDate = null;
//			
//			AsrKeyReport key = new AsrKeyReport();
//			
//			// 设置默认值
//			Date date = new Date();
//			String startDate = dateToStr(DateUtils.addMonths(date, -1));
//			String endDate = dateToStr(date);
//			key.setStartDate(startDate);
//			key.setEndDate(endDate);
//			
//			resMap.put("key", key);
//			
//			// 成功返回
//			resMap.setSuccessReturn();
//			
//		// 业务异常
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return resMap;
//	}
	
	@Override
	public ResponseMap voice(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String dateType = reqMap.getStringValue("统计类型", "dateType", RequestMap.NOT_MUST_INPUT);
		String startDate = reqMap.getStringValue("统计开始时间", "startDate", RequestMap.NOT_MUST_INPUT);
		String endDate = reqMap.getStringValue("统计开始时间", "endDate", RequestMap.NOT_MUST_INPUT);
		String sn = reqMap.getStringValue("SN", "sn", RequestMap.NOT_MUST_INPUT);
		Boolean isSn = reqMap.getBooleanValue("Asr", "isSn", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {			
					
			Long sDate = null;
			Long eDate = null;
			System.out.println("Voice=========" + dateToStr(new Date()));
			if(!StringUtils.isEmpty(startDate)){			
				sDate = strToDate(startDate).getTime();			
				System.out.print(startDate);
			}
			if(!StringUtils.isEmpty(endDate)){			
				eDate = strToDate(endDate).getTime();
				System.out.println("~" + endDate);
			}
						
			// 查询
			List<VoiceReport> lst = reportMapper.voice(dateType, sDate, eDate, sn, isSn);
			for(int i = 0; i < lst.size(); i++){
				VoiceReport vo = lst.get(i);
				double t = 0;
				if(vo.getAllData() != 0){
					//============20170209 bug fix======================
					//t = Float.valueOf((vo.getError() + vo.getEnv() + vo.getTakki())) / Float.valueOf(vo.getAllData());
					t = Float.valueOf((vo.getError() + vo.getEnv() + vo.getTakki())) / Float.valueOf(vo.getAllData()-vo.getNoMarked());
				}				
				DecimalFormat df = new DecimalFormat("######0.00%"); 
				vo.setRate(df.format(t));
			}
		
			resMap.put("list", lst);
			
			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;
	}
	
	@Override
	public ResponseMap asrNlp(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String dateType = reqMap.getStringValue("统计类型", "dateType", RequestMap.NOT_MUST_INPUT);
		String startDate = reqMap.getStringValue("统计开始时间", "startDate", RequestMap.NOT_MUST_INPUT);
		String endDate = reqMap.getStringValue("统计开始时间", "endDate", RequestMap.NOT_MUST_INPUT);
		String sn = reqMap.getStringValue("SN", "sn", RequestMap.NOT_MUST_INPUT);
		String domain = reqMap.getStringValue("Domain", "domain", RequestMap.NOT_MUST_INPUT);
		String intent = reqMap.getStringValue("Intent", "intent", RequestMap.NOT_MUST_INPUT);
		String slot = reqMap.getStringValue("Slot", "slot", RequestMap.NOT_MUST_INPUT);
		String isAsr = reqMap.getStringValue("Asr", "isAsr", RequestMap.NOT_MUST_INPUT);
		Boolean isSn = reqMap.getBooleanValue("Asr", "isSn", RequestMap.NOT_MUST_INPUT);
		Boolean isDomain = reqMap.getBooleanValue("Asr", "isDomain", RequestMap.NOT_MUST_INPUT);
		Boolean isIntent = reqMap.getBooleanValue("Asr", "isIntent", RequestMap.NOT_MUST_INPUT);
		Boolean isSlot = reqMap.getBooleanValue("Asr", "isSlot", RequestMap.NOT_MUST_INPUT);		
		Boolean isAsrBool = isAsr == null || isAsr.equals("0") ? false : true;
		
		//业务处理
		try {			
					
			Long sDate = null;
			Long eDate = null;
			System.out.println("AsrNlp=========" + dateToStr(new Date()));
			if(!StringUtils.isEmpty(startDate)){			
				sDate = strToDate(startDate).getTime();			
				System.out.print(startDate);
			}
			if(!StringUtils.isEmpty(endDate)){			
				eDate = strToDate(endDate).getTime();
				System.out.println("~" + endDate);
			}
						
			// 查询
			List<AsrReport> lst = reportMapper.asrNlp(dateType, sDate, eDate, sn, domain, intent, slot,
					isAsrBool, isSn, isDomain, isIntent, isSlot);
			resMap.put("list", lst);
			
			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;
	}
	
	@Override
	public ResponseMap asrNlpSex(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String dateType = reqMap.getStringValue("统计类型", "dateType", RequestMap.NOT_MUST_INPUT);
		String startDate = reqMap.getStringValue("统计开始时间", "startDate", RequestMap.NOT_MUST_INPUT);
		String endDate = reqMap.getStringValue("统计开始时间", "endDate", RequestMap.NOT_MUST_INPUT);
		String sn = reqMap.getStringValue("SN", "sn", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {			
					
			Long sDate = null;
			Long eDate = null;
			System.out.println("AsrNlp Sex=========" + dateToStr(new Date()));
			if(!StringUtils.isEmpty(startDate)){			
				sDate = strToDate(startDate).getTime();			
				System.out.print(startDate);
			}
			if(!StringUtils.isEmpty(endDate)){			
				eDate = strToDate(endDate).getTime();
				System.out.println("~" + endDate);
			}
						
			// 查询
			List<AsrSexReport> lst = reportMapper.asrNlpSex(dateType, sDate, eDate, sn);
			resMap.put("list", lst);
			
			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;
	}
	
	@Override
	public ResponseMap active(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String dateType = reqMap.getStringValue("统计类型", "dateType", RequestMap.NOT_MUST_INPUT);
		String startDate = reqMap.getStringValue("统计时间", "startDate", RequestMap.MUST_INPUT);
		String endDate = reqMap.getStringValue("统计开始时间", "endDate", RequestMap.NOT_MUST_INPUT);
		String sn = reqMap.getStringValue("SN", "sn", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {			
					
			Long sDate = null;
			Long eDate = null;
			System.out.println("Active=========" + dateToStr(new Date()));
			if(!StringUtils.isEmpty(startDate)){	
				startDate = startDate + " 00:00:00";
				sDate = strToDate(startDate).getTime();			
				System.out.print(startDate);
			}
			if(sDate != null){			
				eDate = sDate + 1000*24*3600 - 1;
				System.out.println("~" + "+1");
			}
						
			// 查询
			List<ActiveReport> lst = reportMapper.active(dateType, sDate, eDate, sn);
			resMap.put("list", lst);
			
			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;
	}
	
	@Override
	public ResponseMap chat(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String dateType = reqMap.getStringValue("统计类型", "dateType", RequestMap.NOT_MUST_INPUT);
		String startDate = reqMap.getStringValue("统计开始时间", "startDate", RequestMap.NOT_MUST_INPUT);
		String endDate = reqMap.getStringValue("统计开始时间", "endDate", RequestMap.NOT_MUST_INPUT);
		String assignId = reqMap.getStringValue("指派", "assignId", RequestMap.NOT_MUST_INPUT);
		Boolean isAssignId = reqMap.getBooleanValue("指派", "isAssignId", RequestMap.NOT_MUST_INPUT);
		String sn = reqMap.getStringValue("SN", "sn", RequestMap.NOT_MUST_INPUT);
		Boolean isSn = reqMap.getBooleanValue("Asr", "isSn", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {			
					
			Long sDate = null;
			Long eDate = null;
			System.out.println("Chat=========" + dateToStr(new Date()));
			if(!StringUtils.isEmpty(startDate)){			
				sDate = strToDate(startDate).getTime();			
				System.out.print(startDate);
			}
			if(!StringUtils.isEmpty(endDate)){			
				eDate = strToDate(endDate).getTime();
				System.out.println("~" + endDate);
			}
						
			// 查询
			List<ChatReport> lst = reportMapper.chat(dateType, sDate, eDate, assignId, isAssignId, sn, isSn);	
			resMap.put("list", lst);
			
			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return resMap;
	}
	
	@Override
	public ResponseMap chatNew(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		Integer page = reqMap.getIntegerValue("页码", "page", RequestMap.NOT_MUST_INPUT);
		// 请求参数
		String startDate = reqMap.getStringValue("统计开始时间", "startDate", RequestMap.NOT_MUST_INPUT);
		String endDate = reqMap.getStringValue("统计开始时间", "endDate", RequestMap.NOT_MUST_INPUT);
		String assignId = reqMap.getStringValue("指派", "assignId", RequestMap.NOT_MUST_INPUT);
		String sn = reqMap.getStringValue("SN", "sn", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {			
					
			Long sDate = null;
			Long eDate = null;
			System.out.println("Chat=========" + dateToStr(new Date()));
			if(!StringUtils.isEmpty(startDate)){			
				sDate = strToDate(startDate).getTime();			
				System.out.print(startDate);
			}
			if(!StringUtils.isEmpty(endDate)){			
				eDate = strToDate(endDate).getTime();
				System.out.println("~" + endDate);
			}
			
			// 用户一栏查询
			int count = reportMapper.chatNewCount(sDate, eDate, assignId, sn);
			// 获取总页数
			int pageCount = RokidUtils.getPageCnt(count);
			if(page == null) page = 1;
			if(page > pageCount){
				page = pageCount;
			}
			// 开始数据下标
			int start = (page - 1) * RokidUtils.getPageSize();
						
			// 查询
			List<ChatNewReport> lst = reportMapper.chatNew(sDate, eDate, assignId, sn, start, RokidUtils.getPageSize());	
			resMap.put("list", lst);
			
			// 一栏记录返回
			resMap.put("page", page);
			resMap.put("pageCount", pageCount);
			resMap.put("pageSize", RokidUtils.getPageSize());
			
			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;
	}
	
	@Override
	public ResponseMap chatNewDel(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String ids = reqMap.getStringValue("删除ID", "ids", RequestMap.MUST_INPUT);
		
		//业务处理
		try {			
					
			// 用户一栏查询
			int count = reportMapper.chatNewDel(ids);
			if(count == 0){ 
				resMap.setFailReturn("删除失败！");
			}
			
			// 成功返回
			resMap.setSuccessReturn();
			
		// 业务异常
		}catch(CannotGetJdbcConnectionException e){
			resMap.setFailReturn("数据库连接超时！");
		}catch(Exception e){
			resMap.setFailReturn(e.getMessage());
			e.printStackTrace();
		}
		
		return resMap;
	}
}