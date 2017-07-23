package com.rokid.soa.service.impl.biaozhu;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rokid.soa.bo.biaozhu.Nlp;
import com.rokid.soa.bo.manage.Config;
import com.rokid.soa.bo.manage.Count;
import com.rokid.soa.bo.manage.Domain;
import com.rokid.soa.bo.manage.Group;
import com.rokid.soa.bo.manage.Intent;
import com.rokid.soa.bo.manage.Upds;
import com.rokid.soa.common.Constants;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.common.RokidUtils;
import com.rokid.soa.dao.biaozhu.NlpDao;
import com.rokid.soa.mapper.biaozhu.NlpMapper;
import com.rokid.soa.mapper.manage.ConfigMapper;
import com.rokid.soa.mapper.manage.DomainMapper;
import com.rokid.soa.mapper.manage.GroupMapper;
import com.rokid.soa.mapper.manage.IntentMapper;
import com.rokid.soa.mapper.manage.UserMapper;
import com.rokid.soa.service.biaozhu.IAsrNlpService;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.service.impl.common.PropertiesUtil;
import com.rokid.soa.vo.manage.IdNameVo;

/**
 * ASRNLP SERVICE Created by tong on 16/10/26.
 */
@Transactional
@Service
public class NlpService extends CommonService implements IAsrNlpService {

	/** 语音标注DAO */
	@Autowired
	private NlpMapper nlpMapper;
	
	/** 用户表MAPPER  */
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private GroupMapper groupMapper;
	
	@Autowired
	private DomainMapper domainMapper;
	
	@Autowired
	private IntentMapper intentMapper;
	
	@Autowired
	private ConfigMapper configMapper;
	
	/**
	 * 初始化接口
	 */
	@Override
	public ResponseMap init(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		//domain
		List<Group> glst = getGroup();
		resMap.put("groupList", glst);					
		
		//domain
		List<Domain> dlst = getDomain(null, null);
		resMap.put("domainList", dlst);	
		
		//intent
		List<Intent> ilst = getIntent(null, null);
		resMap.put("intentList", ilst);	
		
		// 成功返回
		resMap.setSuccessReturn();
		
		return resMap;
	}
	
	/**
	 * 标注更新
	 */
	@Override
	public ResponseMap update(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String id = reqMap.getStringValue("标注ID", "id", RequestMap.MUST_INPUT);
		String domain = reqMap.getStringValue("domain", "domain", RequestMap.NOT_MUST_INPUT);
		String intent = reqMap.getStringValue("intent", "intent", RequestMap.NOT_MUST_INPUT);
		String slot = reqMap.getStringValue("slot", "slot", RequestMap.NOT_MUST_INPUT);
		String newDomain = reqMap.getStringValue("domain", "newDomain", RequestMap.NOT_MUST_INPUT);
		String newIntent = reqMap.getStringValue("intent", "newIntent", RequestMap.NOT_MUST_INPUT);
		String newSlot = reqMap.getStringValue("slot", "newSlot", RequestMap.NOT_MUST_INPUT);
		String act = reqMap.getStringValue("操作类型", "act", RequestMap.MUST_INPUT);
		// 用户ID
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);
		// 1:admin 2:标注者
		Short userType = reqMap.getShortValue(Constants.SESSION_USER_TYPE);
		
		//业务处理
		try {
			
			//标注类型　-1del　　0导入 　1error　2ok			
			Nlp nlp = new Nlp();
			nlp.setId(id);
			nlp.setUpdId(sessionUserId);
			nlp.setUpdTime(RokidUtils.getSysTime());
			
			if("save".equals(act)){
				if(domain==null) domain = "";
				if(intent==null) intent = "";
				if(slot==null) slot = "";
				if(newDomain==null) newDomain = "";
				if(newIntent==null) newIntent = "";
				if(newSlot==null) newSlot = "";
				
				nlp.setNewDomain(newDomain);
				nlp.setNewIntent(newIntent);
				nlp.setNewSlot(newSlot);	
				if(domain.equals(newDomain) && intent.equals(newIntent) && slot.equals(newSlot)){
					nlp.setType((short) 2);
				}else{				
					nlp.setType((short) 1);
				}
			}else if("ignore".equals(act)){
				nlp.setType((short) -1);
			}
			
			nlpMapper.updateByPrimaryKeySelective(nlp);
			
			
			resMap.put("row", nlp);
			
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
	
	/**
	 * 标注更新
	 */
	@Override
	public ResponseMap updates(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		List<Upds> lst = reqMap.getListByType("list", Upds.class);
		// 用户ID
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);
		// 1:admin 2:标注者
		Short userType = reqMap.getShortValue(Constants.SESSION_USER_TYPE);
		
		//业务处理
		try {
			
			for(int i=0; i < lst.size(); i++){
				Upds bo = lst.get(i);
				//标注类型　-1del　　0导入 　1error　2ok			
				Nlp nlp = new Nlp();
				nlp.setId(bo.getId());
				nlp.setType(Short.valueOf(bo.getValue().toString()));
				//重新标注
				if(bo.getValue().toString().equals("0")){
					nlp.setNewDomain("");
					nlp.setNewIntent("");
					nlp.setNewSlot("");
				//已处理
				}else if(bo.getValue().toString().equals("2")){
					
				//删除
				}else if(bo.getValue().toString().equals("-1")){
					nlp.setUpdId(sessionUserId);
				}
				nlp.setUpdTime(RokidUtils.getSysTime());
				nlpMapper.updateByPrimaryKeySelective(nlp);
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

	/**
	 * 语音标注一栏数据查询显示
	 */
	@Override
	public ResponseMap list(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		Integer page = reqMap.getIntegerValue("页码", "page", RequestMap.NOT_MUST_INPUT);
		// 用户
		String userId = reqMap.getStringValue("用户", "user", RequestMap.NOT_MUST_INPUT);
		// SN
		String sn = reqMap.getStringValue("机器码", "sn", RequestMap.NOT_MUST_INPUT);
		// asr
		String asr = reqMap.getStringValue("asr内容", "asr", RequestMap.NOT_MUST_INPUT);
		// domain
		String domain = reqMap.getStringValue("domain", "domain", RequestMap.NOT_MUST_INPUT);
		// intent
		String intent = reqMap.getStringValue("intent", "intent", RequestMap.NOT_MUST_INPUT);
		// 开始时间
		String sDate = reqMap.getStringValue("开始时间", "start", RequestMap.NOT_MUST_INPUT);
		// 结束时间
		String eDate = reqMap.getStringValue("结束时间", "end", RequestMap.NOT_MUST_INPUT);
		// 是否有说明
		Integer slot = reqMap.getIntegerValue("是否有说明", "slot", RequestMap.NOT_MUST_INPUT);
		// 标注类型
		Integer type = reqMap.getIntegerValue("标注类型", "type", RequestMap.NOT_MUST_INPUT);
		// 用户ID
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);
		// 用户TYPE
		Short sessionUserType = reqMap.getShortValue(Constants.SESSION_USER_TYPE);
		
		//业务处理
		try {	
			// 查询时间处理
			Long startDate = null;
			Long endDate = null;
			if(!StringUtils.isEmpty(sDate)) startDate = strToDate(sDate + " 00:00:00").getTime();
			if(!StringUtils.isEmpty(eDate)) endDate = DateUtils.addDays(strToDate(eDate + " 00:00:00"), 1).getTime() - 1;
			
			// 用户一栏查询
			int count = nlpMapper.listCount(startDate, endDate, sn, asr, domain, intent, slot, type);
			// 获取总页数
			int pageCount = RokidUtils.getPageCnt(count);
			if(page == null) page = 1;
			if(page > pageCount){
				page = pageCount;
			}
			// 开始数据下标
			int start = (page - 1) * RokidUtils.getPageSize();
			// 用户一栏查询
			List<Nlp> listBo = nlpMapper.list(startDate, endDate, sn, asr, domain, intent, slot, type, start, RokidUtils.getPageSize());

			// 一栏记录返回
			resMap.put("page", page);
			resMap.put("pageCount", pageCount);
			resMap.put("pageSize", RokidUtils.getPageSize());
			
			// 一栏记录返回
			resMap.put("list", listBo); 
			
			//String path = PropertiesUtil.getConfiguration().getString("nlpPath");
			// audio path
			Config configTmp = configMapper.selectByPrimaryKey("nlpPath");
			String path = "";
			if(configTmp != null) path = configTmp.getValue();
			resMap.put("path", path);
			
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
	
	/**
	 * 统计
	 */
	@Override
	public ResponseMap count(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();

		// domain
		String domain = reqMap.getStringValue("domain", "domain", RequestMap.NOT_MUST_INPUT);
		// 开始时间
		String sDate = reqMap.getStringValue("开始时间", "start", RequestMap.NOT_MUST_INPUT);
		// 结束时间
		String eDate = reqMap.getStringValue("结束时间", "end", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {
			
			List<String> domainLst = null;
			if(domain != null && domain.substring(0, 8).equals("groupId:")){
				Integer groupId = Integer.valueOf(domain.substring(8));
				List<Domain> lst = domainMapper.list(null, groupId);
				domainLst = new ArrayList<String>();
				for(int i =0 ; i < lst.size(); i++){
					domainLst.add(lst.get(i).getDomain());
				}
				if(domainLst.size() == 0) domainLst.add("testtesttest");
				domain = null;
			}
			
			// 查询时间处理
			Long startDate = null;
			Long endDate = null;
			if(!StringUtils.isEmpty(sDate)) startDate = strToDate(sDate + " 00:00:00").getTime();
			if(!StringUtils.isEmpty(eDate)) endDate = DateUtils.addDays(strToDate(eDate + " 00:00:00"), 1).getTime() - 1;
			
			// 用户一栏查询
			List<Count> list = nlpMapper.count("d", domain, domainLst, startDate, endDate);

			// 一栏记录返回
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
	
	/**
	 * domain分组查询
	 */
	@Override
	public ResponseMap selDomainGroup(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		// groupId
		Integer groupId = reqMap.getIntegerValue("groupId", "groupId", RequestMap.NOT_MUST_INPUT);
		
		//业务处理
		try {
			
			List<Domain> list = getDomain(null, groupId);

			// 一栏记录返回
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
	
	/**
	 * domain分组更新
	 */
	@Override
	public ResponseMap updDomainGroup(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		List<Domain> dList = reqMap.getListByType("dList", Domain.class);
		
		// 请求参数
		List<Group> gList = reqMap.getListByType("gList", Group.class);
		
		//业务处理
		try {
			
			if(gList == null) gList = new ArrayList<Group>();
			for(int i=0; i < gList.size(); i++){
				Group bo = gList.get(i);
				if(bo.getAct().equals("add")){
					if(!StringUtils.isEmpty(bo.getName())){
						Group g = groupMapper.selectByName(bo.getName());
						if(g == null){
							bo.setId(null);
							groupMapper.insert(bo);
						}
					}
				}else if(bo.getAct().equals("del")){
					groupMapper.deleteByPrimaryKey(bo.getId());
				}else if(bo.getAct().equals("edit")){
					groupMapper.updateByPrimaryKeySelective(bo);
				}
			}
			
			if(dList == null) dList = new ArrayList<Domain>();
			for(int i=0; i < dList.size(); i++){
				Domain bo = dList.get(i);
				if(StringUtils.isNotEmpty(bo.getGroupName())){
					Group g = groupMapper.selectByName(bo.getGroupName());
					if(g != null){
						bo.setGroupId(g.getId());
					}
				}
				domainMapper.updateByPrimaryKey(bo);
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
	
	public List<Group> getGroup(){
		return groupMapper.list();
	}
	
	public List<Domain> getDomain(Integer type, Integer groupId){
		return domainMapper.list(type, groupId);
	}
	
	public List<Intent> getIntent(String domain, Integer type){
		return intentMapper.list(domain, type);
	}

	@Override
	public ResponseMap updWavErr(RequestMap reqMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
