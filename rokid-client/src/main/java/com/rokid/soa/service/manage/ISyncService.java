package com.rokid.soa.service.manage;

import com.rokid.soa.common.ResponseMap;

/**
 * 数据导入 SERVICE
 * Created by fanglh on 16/10/28.
 */

public interface ISyncService {
	
	/*
	 * 定时删除已分配但未标注的数据
	 */
	public void DelWorkRec() throws Exception;
	
	public void DelFilterRec() throws Exception;
    
	/*
	 * 同步语音数据
	 */
	public ResponseMap SyncVoice() throws Exception ;
	
	/*
	 * 同步ASR数据
	 */
	public ResponseMap SyncAsr() throws Exception ;
		
	/*
	 * 同步Chat话题数据
	 */
	public ResponseMap SyncChat() throws Exception ;
	
	/*
	 * 同步Question数据
	 */
	public ResponseMap SyncQuestion() throws Exception ;
	
	/*
	 * 同步Answer数据
	 */
	public ResponseMap SyncAnswer() throws Exception ;
	
	/**
	 * domain
	 * @throws Exception
	 */
	public ResponseMap SyncDomain() throws Exception;
}
