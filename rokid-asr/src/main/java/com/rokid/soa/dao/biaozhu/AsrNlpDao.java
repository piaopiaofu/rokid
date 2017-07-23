package com.rokid.soa.dao.biaozhu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rokid.soa.bo.biaozhu.AsrNlp;
import com.rokid.soa.dao.common.CommDao;
import com.rokid.soa.mapper.biaozhu.AsrNlpMapper;


@Repository
/**
 * ASRNLP标注DAO
 */
public class AsrNlpDao extends CommDao {

	@Autowired
	private AsrNlpMapper asrNlpMapper;

	/**
	 * 语音标注一栏数量
	 * @param userName
	 * @param password
	 * @return
	 */
	public int listCount(String userId, String sn, Long startDate, Long endDate, Integer asrLen, String type, String asrType) {
		return asrNlpMapper.listCount(userId, sn, startDate, endDate, asrLen, type, asrType);
	}
	
	/**
	 * 语音标注一栏
	 * @param userName
	 * @param password
	 * @return
	 */
	public List<AsrNlp> list(Integer start, Integer pageSize, String userId, String sn, Long startDate, Long endDate, Integer asrLen, String type, String asrType) {
		return asrNlpMapper.list(start, pageSize, userId, sn, startDate, endDate, asrLen, type, asrType);
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
		return asrNlpMapper.getWorkRecordByWorkId(startDate, endDate, user);
	}
	
	/**
	 * 获取正常指派数据
	 * @param user
	 * @return
	 */
	public int getWorkRecordByNormal(String user){
		return asrNlpMapper.getWorkRecordByNormal(user);
	}
	
	/**
	 * 用户退出删除分配未标注数据
	 * @param user
	 * @return
	 */
	public int loginOutDelWorkDate(String user){
		return asrNlpMapper.loginOutDelWorkDate(user);
	}
	
	public int  biaozhu(String id, String updId, String updTime, String editAdmin, String extField, Short asrtype, String asrEdit, Short asrEditCnt, Short type){
		return asrNlpMapper.biaozhu(id, updId, updTime, editAdmin, extField, asrtype, asrEdit, asrEditCnt, type);
	}
}