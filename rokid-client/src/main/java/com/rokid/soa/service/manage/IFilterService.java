package com.rokid.soa.service.manage;

import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;

/**
 * 数据导入 SERVICE
 * Created by fanglh on 16/10/28.
 */

public interface IFilterService {
	
	/*
	 * 获取列表
	 */
	public ResponseMap list(RequestMap reqMap) throws Exception ;
    
	/*
	 * 保存设定
	 */
	public ResponseMap update(RequestMap reqMap) throws Exception ;
	
	/*
	 * 删除
	 */
	public ResponseMap delete(RequestMap reqMap) throws Exception ;
	
	/*
	 * 批量添加
	 */
	public ResponseMap snList(RequestMap reqMap) throws Exception ;
}
