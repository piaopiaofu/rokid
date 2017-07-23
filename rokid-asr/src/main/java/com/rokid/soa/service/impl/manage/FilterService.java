package com.rokid.soa.service.impl.manage;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rokid.soa.bo.manage.Filter;
import com.rokid.soa.common.Constants;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.common.RokidUtils;
import com.rokid.soa.mapper.manage.FilterMapper;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.service.manage.IFilterService;

/**
 * 黑名单设定服务 Created by tong on 16/11/17.
 */
@Transactional
@Service
public class FilterService extends CommonService implements IFilterService {

	@Autowired
	private FilterMapper filterMapper;
	
	/*
	 * 保存设定
	 */
	@Override
	public ResponseMap snList(RequestMap reqMap) throws Exception {	
		
		String snStr = reqMap.getStringValue("机器码列表", "snList", RequestMap.MUST_INPUT);
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		try{	
			
			snStr = snStr.replace("，", ",");
			snStr = snStr.replace("\n", ",");
			String[] snList = snStr.split(",");
			
			int succ = 0;
			int err = 0;
			for(int i =  0; i < snList.length; i++){
				if(!StringUtils.isEmpty(snList[i])){
					Filter bo = filterMapper.findSn(snList[i]);
					if(bo == null){
						bo = new Filter();
						bo.setSn(snList[i]);
						bo.setCrtTime(RokidUtils.getSysTime());
						bo.setCrtUser(sessionUserId);
						filterMapper.insert(bo);
						succ++;
					}else{
						err++;
					}
				}
			}
			resMap.put("succ", "成功导入：" + succ + "件");
			resMap.put("err", "重复数据：" + err + "件");
			resMap.setSuccessReturn();
			
			return resMap;
			
		}catch(Exception e){
			resMap.setFailReturn("系统错误！");
			e.printStackTrace();
			return resMap;
		}finally{

		}
	}	

	/*
	 * 保存设定
	 */
	@Override
	public ResponseMap list(RequestMap reqMap) throws Exception {	
		
		Integer page = reqMap.getIntegerValue("页码", "page", RequestMap.NOT_MUST_INPUT);
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		try{	

			// 用户一栏查询
			int count = filterMapper.listCount();
			// 获取总页数
			int pageCount = RokidUtils.getPageCnt(count);
			if(page == null) page = 1;
			if(page > pageCount){
				page = pageCount;
			}
			// 开始数据下标
			int start = (page - 1) * RokidUtils.getPageSize();
			// 一栏查询			
			List<Filter> lst = filterMapper.list(start, RokidUtils.getPageSize());
			
			// 一栏记录返回
			resMap.put("page", page);
			resMap.put("pageCount", pageCount);
			resMap.put("pageSize", RokidUtils.getPageSize());
		
			resMap.put("list", lst);
			resMap.setSuccessReturn();
			
			return resMap;
			
		}catch(Exception e){
			resMap.setFailReturn("系统错误！");
			e.printStackTrace();
			return resMap;
		}finally{

		}
	}	
	
	/*
	 * 保存设定
	 */
	@Override
	public ResponseMap update(RequestMap reqMap) throws Exception {	
		
		// 请求参数
		Integer id = reqMap.getIntegerValue("ID", "id", RequestMap.NOT_MUST_INPUT);
		String sn = reqMap.getStringValue("机器号", "sn", RequestMap.MUST_INPUT);
		String memo = reqMap.getStringValue("备注", "memo", RequestMap.NOT_MUST_INPUT);
		// 用户ID
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);
				
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		try{	
			
			Filter filter = new Filter();
			filter.setId(id);
			filter.setSn(sn);
			if(memo==null) memo="";
			filter.setMemo(memo);
			
			Filter tmpFilter = filterMapper.findSn(sn);
			if(id == null){
				if(tmpFilter != null){
					resMap.setFailReturn("机器码已存在");
					return resMap;
				}else{
					// 标注用户
					filter.setCrtUser(sessionUserId);
					// 标注时间
					filter.setCrtTime(RokidUtils.getSysTime());
					filterMapper.insert(filter);
				}
			}else{
				if(tmpFilter != null && !tmpFilter.getId().equals(id)){
					resMap.setFailReturn("机器码已存在");
					return resMap;
				}else{
					// 标注用户
					//filter.setUpdId(sessionUserId);
					// 标注时间
					//filter.setUpdTime(RokidUtils.getSysTime());
					filterMapper.updateByPrimaryKeySelective(filter);
				}
			}			
			resMap.setSuccessReturn();
			resMap.put("row", filter);
			return resMap;
			
		}catch(Exception e){
			resMap.setFailReturn("系统错误！");
			e.printStackTrace();
			return resMap;
		}finally{

		}
	}	
	
	/*
	 * 删除
	 */
	@Override
	public ResponseMap delete(RequestMap reqMap) throws Exception {	
		
		// 请求参数
		Integer id = reqMap.getIntegerValue("ID", "id", RequestMap.NOT_MUST_INPUT);			
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		try{	
			
			filterMapper.deleteByPrimaryKey(id);
			resMap.setSuccessReturn();
			return resMap;
			
		}catch(Exception e){
			resMap.setFailReturn("系统错误！");
			e.printStackTrace();
			return resMap;
		}finally{

		}
	}	
}