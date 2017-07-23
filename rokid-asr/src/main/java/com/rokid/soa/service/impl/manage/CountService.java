package com.rokid.soa.service.impl.manage;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rokid.soa.bo.manage.CountUserYm;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.dao.manage.CountDao;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.service.manage.ICountService;

/**
 * 统计SERVICE Created by tong on 16/10/28.
 */
@Transactional
@Service
public class CountService extends CommonService implements ICountService {

	/** 统计DAO */
	@Autowired
	private CountDao countDao;
	
	@Override
	public ResponseMap userYm(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String userId = reqMap.getStringValue("用户名", "userId", RequestMap.MUST_INPUT);
		// 开始时间
		String sDate = reqMap.getStringValue("开始时间", "start", RequestMap.NOT_MUST_INPUT);
		// 结束时间
		String eDate = reqMap.getStringValue("结束时间", "end", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {
			
			// 查询时间处理
			Long startDate = null;
			Long endDate = null;
			if(!StringUtils.isEmpty(sDate)) startDate = strToDate(sDate + " 00:00:00").getTime();
			if(!StringUtils.isEmpty(eDate)) endDate = DateUtils.addDays(strToDate(eDate + " 00:00:00"), 1).getTime() - 1;
			
			// 查询
			CountUserYm userYm= countDao.userYm(userId, startDate, endDate);
			resMap.put("vo", userYm);
			
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
	
	public static Date strToDate(String strDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	}
	
	public static String dateToStr(java.util.Date dateDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(dateDate);
	   return dateString;
	}
	
	public String longToDate(Long millSec){
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date= new Date(millSec);
            return sdf.format(date);
    }
}