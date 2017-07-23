package com.rokid.soa.service.impl.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rokid.soa.bo.manage.Config;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.mapper.manage.ConfigMapper;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.service.manage.IConfigService;

/**
 * 配置设定服务 Created by tong on 16/11/17.
 */
@Transactional
@Service
public class ConifgService extends CommonService implements IConfigService {

	@Autowired
	private ConfigMapper configMapper;

	/*
	 * 保存设定
	 */
	@Override
	public ResponseMap list(RequestMap reqMap) throws Exception {	
		
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		try{	
			
			List<Config> lst = configMapper.list();
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
		String id = reqMap.getStringValue("配置ID", "id", RequestMap.MUST_INPUT);
		String name = reqMap.getStringValue("配置说明", "name", RequestMap.MUST_INPUT);
		String value = reqMap.getStringValue("配置内容", "value", RequestMap.MUST_INPUT);
				
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		try{	
			
			Config config = new Config();
			config.setId(id);
			config.setName(name);
			config.setValue(value);
			
			Config configTmp = configMapper.selectByPrimaryKey(id);
			if(configTmp == null){
				configMapper.insert(config);
			}else{
				configMapper.updateByPrimaryKey(config);
			}			
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