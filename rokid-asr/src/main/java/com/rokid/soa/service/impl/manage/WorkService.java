package com.rokid.soa.service.impl.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rokid.soa.bo.manage.CountUserYm;
import com.rokid.soa.bo.manage.Work;
import com.rokid.soa.bo.manage.WorkCnt;
import com.rokid.soa.common.BeanUtil;
import com.rokid.soa.common.Constants;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.common.RokidUtils;
import com.rokid.soa.mapper.biaozhu.AsrNlpMapper;
import com.rokid.soa.mapper.biaozhu.QuestionMapper;
import com.rokid.soa.mapper.biaozhu.VoiceTriggerMapper;
import com.rokid.soa.mapper.manage.WorkCntMapper;
import com.rokid.soa.mapper.manage.WorkMapper;
import com.rokid.soa.mapper.manage.WorkUserMapper;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.service.manage.IWorkService;
import com.rokid.soa.vo.manage.WorkVo;
/**
 * 用户SERVICE Created by tong on 16/10/22.
 */
@Transactional
@Service
public class WorkService extends CommonService implements IWorkService {

	/** 任务分工指派 */
	@Autowired
	private WorkMapper workMapper;
	
	@Autowired
	private WorkUserMapper workUserMapper;
	
	@Autowired
	private WorkCntMapper workCntMapper;
	
	@Autowired
	private VoiceTriggerMapper voiceTriggerMapper;
	
	@Autowired
	private AsrNlpMapper asrNlpMapper;
	
	@Autowired
	private QuestionMapper chatMapper;
		
	@Override
	public ResponseMap list(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		Integer page = reqMap.getIntegerValue("页码", "page", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {
			List<WorkVo> list= new ArrayList<WorkVo>();
			// 用户一栏查询
			int count = workMapper.listCount();
			// 获取总页数
			int pageCount = RokidUtils.getPageCnt(count);
			if(page == null) page = 1;
			if(page > pageCount){
				page = pageCount;
			}
			// 开始数据下标
			int start = (page - 1) * RokidUtils.getPageSize();
			// 用户一栏查询
			List<Work> listBo = workMapper.list(start, RokidUtils.getPageSize());
			for(int i = 0; i < listBo.size(); i++){
				WorkVo vo = new WorkVo();
				Work bo = listBo.get(i);
				BeanUtil.copyProperties(vo, bo);
							
				list.add(vo);
			}
			
			// 一栏记录返回
			resMap.put("page", page);
			resMap.put("pageCount", pageCount);
			resMap.put("pageSize", RokidUtils.getPageSize());
			resMap.put("list", list);
			
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
	public ResponseMap update(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String id = reqMap.getStringValue("指派ID", "id", RequestMap.NOT_MUST_INPUT);
		//String dateType = reqMap.getStringValue("日期类型", "dateType", RequestMap.MUST_INPUT);
		//String time = reqMap.getStringValue("指派时间", "time", RequestMap.MUST_INPUT);
		String stime = reqMap.getStringValue("指派开始时间", "startTime", RequestMap.MUST_INPUT);
		String etime = reqMap.getStringValue("指派结束时间", "endTime", RequestMap.MUST_INPUT);
		Boolean voice = reqMap.getBooleanValue("激活语音", "voice", RequestMap.NOT_MUST_INPUT);
		Boolean asr = reqMap.getBooleanValue("语音识别", "asr", RequestMap.NOT_MUST_INPUT);
		Boolean chat = reqMap.getBooleanValue("聊天标注", "chat", RequestMap.NOT_MUST_INPUT);
		String memo = reqMap.getStringValue("备注", "memo", RequestMap.NOT_MUST_INPUT);
		//List<IdVal> userList = reqMap.getListByType("userList", IdVal.class);
		// 用户ID
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);

		//业务处理
		try {
			
			Long startDate = null;
			Long endDate = null;
			if(!StringUtils.isEmpty(stime)) startDate = strToDate(stime + " 00:00:00").getTime();
			if(!StringUtils.isEmpty(etime)) endDate = DateUtils.addDays(strToDate(etime + " 00:00:00"), 1).getTime() - 1;
			
			if(asr==null) asr = false;
			if(voice==null) voice = false;
			if(chat==null) chat = false;
			
			// 检查当前时间的指派类型是否已经存在
			CountUserYm cnt = workMapper.workCount(startDate, endDate, id);
			if(cnt != null && voice && cnt.getVoiceCnt() > 0){
				resMap.setFailReturn("当前指派时间，与已原激活语音指派时间段重复！");
				return resMap;
			}else if(cnt != null && asr && cnt.getAsrCnt() > 0){
				resMap.setFailReturn("当前指派时间，与已原语音识别指派时间段重复！");
				return resMap;
			}
			
			Work bo = new Work();
			// ID
			bo.setId(id);			
			bo.setAsr(asr);			
			bo.setVoice(voice);			
			bo.setChat(chat);
			if(memo == null) memo = "";
			bo.setMemo(memo);	
			bo.setStartTime(startDate);
			bo.setEndTime(endDate);

			int ret = 0;
			if(id != null){
				// 数据更新
				ret = workMapper.updateByPrimaryKeySelective(bo);				
				if(ret == 0){
					resMap.setFailReturn("指派不存在或已被删除！");
					return resMap;
				}
				
				// 更新当天结束时间为null
				String nowDate = dateToStrS(new Date());
				WorkCnt workCnt = new WorkCnt();
				workCnt.setTime(nowDate);
				if(asr == true && voice == true){
					workCnt.setType(null);
				}else if(asr == true){
					workCnt.setType(Constants.ASR_TYPE);
				}else if(voice == true){
					workCnt.setType(Constants.VOICE_TYPE);
				}
				workCntMapper.updateEndTimeNull(workCnt);
				
			}else{
				bo.setId(RokidUtils.getGUID());
				bo.setTime("");
				bo.setUsers("");
				bo.setCrtTime(RokidUtils.getSysTime());
				bo.setCrtUser(sessionUserId);
				// 数据插入
				ret = workMapper.insert(bo);				
				//返回ID
				resMap.put("id", bo.getId());
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
	
	@Override
	public ResponseMap delete(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String id = reqMap.getStringValue("分配ID", "id", RequestMap.MUST_INPUT);
				
		//业务处理
		try {
			
			// 旧数据保存
			Work oldBo = workMapper.selectByPrimaryKey(id);
			if(oldBo == null){
				resMap.setFailReturn("指派不存在或已被删除！");
				return resMap;
			}
			
			workMapper.deleteByPrimaryKey(id);
			
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