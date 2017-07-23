package com.rokid.soa.dao.biaozhu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rokid.soa.bo.biaozhu.VoiceTrigger;
import com.rokid.soa.dao.common.CommDao;
import com.rokid.soa.mapper.biaozhu.VoiceTriggerMapper;


@Repository
/**
 * 语音标注DAO
 */
public class VoiceTriggerDao extends CommDao {

	@Autowired
	private VoiceTriggerMapper voiceTriggerMapper;

	/**
	 * 语音标注一栏数量
	 * @param userName
	 * @param password
	 * @return
	 */
	public int listCount(String userId, String sn, Long startDate, Long endDate, String type) {
		return voiceTriggerMapper.listCount(userId, sn, startDate, endDate, type);
	}
	
	/**
	 * 语音标注一栏
	 * @param userName
	 * @param password
	 * @return
	 */
	public List<VoiceTrigger> list(Integer start, Integer pageSize, String userId, String sn, Long startDate, Long endDate, String type) {
		return voiceTriggerMapper.list(start, pageSize, userId, sn, startDate, endDate, type);
	}
	
	/**
	 * 指派数据给标注者
	 * @param startDate
	 * @param endDate
	 * @param workId
	 * @param user
	 * @return
	 */
	public int getWorkRecordByWorkId(Long startDate, Long endDate, String user){
		return voiceTriggerMapper.getWorkRecordByWorkId(startDate, endDate, user);
	}
	
	/**
	 * 获取正常指派数据
	 * @param user
	 * @return
	 */
	public int getWorkRecordByNormal(String user){
		return voiceTriggerMapper.getWorkRecordByNormal(user);
	}
	
	public int updateNoMark(String id, String userId){
		return voiceTriggerMapper.updateNoMark(id, userId);
	}
	
	/**
	 * 用户退出删除分配未标注数据
	 * @param user
	 * @return
	 */
	public int loginOutDelWorkDate(String user){
		return voiceTriggerMapper.loginOutDelWorkDate(user);
	}
	
	/**
	 * 2期 标注
	 * @param id
	 * @param updId
	 * @param updTime
	 * @param editAdmin
	 * @param extField
	 * @param type
	 * @return
	 */
	public int biaozhu(String id, String updId, String updTime, String editAdmin, String extField, String type){
		return voiceTriggerMapper.biaozhu(id, updId, updTime, editAdmin, extField, type);
	}
}