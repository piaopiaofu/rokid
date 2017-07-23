package com.rokid.soa.service.impl.biaozhu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rokid.soa.bo.biaozhu.AsrNlp;
import com.rokid.soa.bo.manage.Config;
import com.rokid.soa.bo.manage.Work;
import com.rokid.soa.bo.manage.WorkCnt;
import com.rokid.soa.bo.manage.WorkCntKey;
import com.rokid.soa.bo.manage.WorkVer;
import com.rokid.soa.bo.manage.WorkVerKey;
import com.rokid.soa.common.BeanUtil;
import com.rokid.soa.common.Constants;
import com.rokid.soa.common.JsonUtil;
import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;
import com.rokid.soa.common.RokidUtils;
import com.rokid.soa.dao.biaozhu.AsrNlpDao;
import com.rokid.soa.mapper.manage.ConfigMapper;
import com.rokid.soa.mapper.manage.UserMapper;
import com.rokid.soa.mapper.manage.WorkCntMapper;
import com.rokid.soa.mapper.manage.WorkMapper;
import com.rokid.soa.mapper.manage.WorkUserMapper;
import com.rokid.soa.mapper.manage.WorkVerMapper;
import com.rokid.soa.service.biaozhu.IAsrNlpService;
import com.rokid.soa.service.impl.common.CommonService;
import com.rokid.soa.service.impl.common.WordSequenceAligner;
import com.rokid.soa.vo.biaozhu.AsrNlpVo;
import com.rokid.soa.vo.manage.IdNameVo;

/**
 * ASRNLP SERVICE Created by tong on 16/10/26.
 */
@Transactional
@Service
public class AsrNlpService extends CommonService implements IAsrNlpService {

	/** 语音标注DAO */
	@Autowired
	private AsrNlpDao asrNlpDao;
	
	/** 指派主表MAPPER  */
	@Autowired
	private WorkMapper workMapper;
	
	/** 指派用户表MAPPER  */
	@Autowired
	private WorkUserMapper workUserMapper;
	
	/** 用户表MAPPER  */
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ConfigMapper configMapper;	

	@Autowired
	private WorkCntMapper workCntMapper;
	
	@Autowired
	private WorkVerMapper workVerMapper;
	
	/**
	 * 更新语音无法播放错误
	 */
	@Override
	public ResponseMap updWavErr(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		// 请求参数
		String id = reqMap.getStringValue("标注ID", "id", RequestMap.MUST_INPUT);
		String path = reqMap.getStringValue("语音路径", "path", RequestMap.MUST_INPUT);
		Integer status = reqMap.getIntegerValue("语音路径", "status", RequestMap.NOT_MUST_INPUT);
		
		try{
			if(status == null){
				status = checkWav(id, path, null, asrNlpDao);
			}else if(status == 200){
				status = 405;
			}
			
			if(status != null && status == 405){
				AsrNlp bo = new AsrNlp();
				bo.setId(id);
				bo.setFstatus(status);
				asrNlpDao.updateByPrimaryKeySelective(bo);
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
	 * 标注更新
	 */
	@Override
	public ResponseMap update(RequestMap reqMap) {
		// 返回MAP对象
		ResponseMap resMap = new ResponseMap();
		
		// 请求参数
		String id = reqMap.getStringValue("标注ID", "id", RequestMap.MUST_INPUT);
		//String domain = reqMap.getStringValue("Domain", "domain", RequestMap.MUST_INPUT);
		//String intent = reqMap.getStringValue("Intent", "intent", RequestMap.MUST_INPUT);
		//String slot = reqMap.getStringValue("Slot", "slot", RequestMap.MUST_INPUT);
		Short asrType = reqMap.getShortValue("标注修改类型", "asrType", RequestMap.NOT_MUST_INPUT);
		Short isClkAsrType = reqMap.getShortValue("是否AsrType改变", "isClkAsrType", RequestMap.NOT_MUST_INPUT);
		String asr = reqMap.getStringValue("标注内容", "asr", RequestMap.NOT_MUST_INPUT);
		String asrEdit = reqMap.getStringValue("标注修改内容", "asrEdit", RequestMap.NOT_MUST_INPUT);
		Short asrEditCnt = reqMap.getShortValue("标注修改数量", "asrEditCnt", RequestMap.NOT_MUST_INPUT);
		Short type = reqMap.getShortValue("标注类型", "type", RequestMap.NOT_MUST_INPUT);
		String updName = reqMap.getStringValue("标注用户", "updName", RequestMap.NOT_MUST_INPUT);
		// 用户ID
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);
		// 1:admin 2:标注者
		Short userType = reqMap.getShortValue(Constants.SESSION_USER_TYPE);
		String userName = reqMap.getStringValue(Constants.SESSION_USER_NAME);
		
		//业务处理
		try {
			if(asr != null && asrEdit != null ){
				WordSequenceAligner werEval = new WordSequenceAligner();
				WordSequenceAligner.Alignment a = werEval.align(asr.split(""), asrEdit.split(""));
				asrEditCnt = (short) (a.numInsertions + a.numDeletions + a.numSubstitutions);
			}
			
			String updId = "-1";
			String updTime = "-1";
			String editAdmin = "-1";
			String extField = "-1";
			String extFieldStr = "{\"asrType\":"+asrType+",\"asrEdit\":\""+(asrEdit==null?null:asrEdit.replace("\"", "\\\""))+"\",\"asrEditCnt\":"+asrEditCnt+",\"type\":"+type+"}";
			Short asrTypes =  -1;
			String asrEdits =  "-1";
			Short asrEditCnts =  -1;
			Short types =  -1;
			
			AsrNlp oldbo = (AsrNlp)asrNlpDao.selectByPrimaryKey(new AsrNlp(), id);
			if(oldbo == null){
				resMap.setFailReturn("数据不存在或已被删除！");
				return resMap;
			}
			AsrNlpVo vo = new AsrNlpVo();
			BeanUtil.copyProperties(vo, oldbo);
			vo.setUpdName(updName);
			vo.setEditAdmin(vo.getEditAdmin());

			if(userType == 1){ 														//管理员
				if(oldbo.getAsrType() == null){											//未标注
					updId = sessionUserId;													//标注更新
					updTime = String.valueOf(RokidUtils.getSysTime());
					asrTypes = asrType;
					asrEdits = asrEdit;
					asrEditCnts = asrEditCnt;					
					types = type;
					vo.setUpdName(userName);
					vo.setAsrType(asrType);
					vo.setAsrEdit(asrEdit);
					vo.setAsrEditCnt(asrEditCnt);
					vo.setType(type);
				}else{																	//已标注
					if(oldbo.getUpdId() == null || oldbo.getUpdId().equals(sessionUserId)){	//管理员标注的数据	
						if(asrType == oldbo.getAsrType() && isClkAsrType == 1){					//相同类型，取消标注
							updId = null;	
							updTime = null;
							asrTypes = null;
							asrEdits = null;
							asrEditCnts = null;					
							types = null;
							vo.setUpdName(null);
							vo.setAsrType(null);
							vo.setAsrEdit(null);
							vo.setAsrEditCnt(null);
							vo.setType(null);
						}else{																	//不同类型，更新时间及标注			
							updTime = String.valueOf(RokidUtils.getSysTime());
							asrTypes = asrType;
							asrEdits = asrEdit;
							asrEditCnts = asrEditCnt;					
							types = type;
							vo.setAsrType(asrType);
							vo.setAsrEdit(asrEdit);
							vo.setAsrEditCnt(asrEditCnt);
							vo.setType(type);
						}
					}else{																	//非管理标注数据
						if(asrType != null && asrType == -1){
							String ext = oldbo.getExtField();
							if(ext != null){
								AsrNlp tmp = (AsrNlp)JsonUtil.jsonToBean(ext, AsrNlp.class);
								asrTypes = tmp.getAsrType();
								asrEdits = tmp.getAsrEdit();
								asrEditCnts = tmp.getAsrEditCnt();
								types = tmp.getType();
								editAdmin = "0";
								vo.setEditAdmin(Short.valueOf(editAdmin));
								vo.setAsrType(asrTypes);
								vo.setAsrEdit(asrEdits);
								vo.setAsrEditCnt(asrEditCnts);
								vo.setType(types);
							}							
						}else{
							editAdmin = "1";
							asrTypes = asrType;
							asrEdits = asrEdit;
							asrEditCnts = asrEditCnt;					
							types = type;
							if(StringUtils.isEmpty(oldbo.getExtField())){
								extField = "{\"asrType\":"+oldbo.getAsrType()+",\"asrEdit\":\""+oldbo.getAsrEdit()+"\",\"asrEditCnt\":"+oldbo.getAsrEditCnt()+",\"type\":"+oldbo.getType()+"}";;
							}
							vo.setEditAdmin(Short.valueOf(editAdmin));
							vo.setAsrType(asrType);
							vo.setAsrEdit(asrEdit);
							vo.setAsrEditCnt(asrEditCnt);
							vo.setType(type);
						}
					}
				}
			}
			if(userType == 2){ 														//标注者
				if(oldbo.getAsrType() == null){											//未标注
					updId = sessionUserId;													//标注更新
					updTime = String.valueOf(RokidUtils.getSysTime());
					asrTypes = asrType;
					asrEdits = asrEdit;
					asrEditCnts = asrEditCnt;					
					types = type;
					extField = extFieldStr;
					vo.setUpdName(userName);
					vo.setAsrType(asrType);
					vo.setAsrEdit(asrEdit);
					vo.setAsrEditCnt(asrEditCnt);
					vo.setType(type);
				}else{																	//已标注
					if(asrType == oldbo.getAsrType() && isClkAsrType == 1){					//相同类型，取消标注
						//updId = null;	
						updTime = null;
						asrTypes = null;
						asrEdits = null;
						asrEditCnts = null;					
						types = null;
						extField = null;
						//vo.setUpdName(null);
						vo.setAsrType(null);
						vo.setAsrEdit(null);
						vo.setAsrEditCnt(null);
						vo.setType(null);
					}else{																	//不同类型，更新时间及标注			
						updTime = String.valueOf(RokidUtils.getSysTime());
						asrTypes = asrType;
						asrEdits = asrEdit;
						asrEditCnts = asrEditCnt;					
						types = type;
						extField = extFieldStr;
						vo.setAsrType(asrType);
						vo.setAsrEdit(asrEdit);
						vo.setAsrEditCnt(asrEditCnt);
						vo.setType(type);
					}
				}
			}
			asrNlpDao.biaozhu(id, updId, updTime, editAdmin, extField, asrTypes, asrEdits, asrEditCnts, types);
			
			resMap.put("row", vo);
			
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
		// 开始时间
		String sDate = reqMap.getStringValue("开始时间", "start", RequestMap.NOT_MUST_INPUT);
		// 结束时间
		String eDate = reqMap.getStringValue("结束时间", "end", RequestMap.NOT_MUST_INPUT);
		// 文本长度
		Integer asrLen = reqMap.getIntegerValue("文本长度", "asrLen", RequestMap.NOT_MUST_INPUT);
		// 标注结果
		String asrType = reqMap.getStringValue("标注结果", "asrType", RequestMap.NOT_MUST_INPUT);
		// 标注类型
		String type = reqMap.getStringValue("标注类型", "type", RequestMap.NOT_MUST_INPUT);
		// 是否获取指派数据
		Integer getWork = reqMap.getIntegerValue("是否获取指派数据", "getWork", RequestMap.NOT_MUST_INPUT);
		// 用户ID
		String sessionUserId = reqMap.getStringValue(Constants.SESSION_USER_ID);
		// 用户TYPE
		Short sessionUserType = reqMap.getShortValue(Constants.SESSION_USER_TYPE);
		
		List<AsrNlpVo> list= new ArrayList<AsrNlpVo>();
		
		//业务处理
		try {	
			//当管理员账号登录时，查询所有数据
			if(sessionUserType != null && sessionUserType.compareTo((short) 1) == 0){		
				
				// 查询时间处理
				Long startDate = null;
				Long endDate = null;
				if(!StringUtils.isEmpty(sDate)) startDate = strToDate(sDate + " 00:00:00").getTime();
				if(!StringUtils.isEmpty(eDate)) endDate = DateUtils.addDays(strToDate(eDate + " 00:00:00"), 1).getTime() - 1;
				
				// 用户一栏查询
				int count = asrNlpDao.listCount(userId, sn, startDate, endDate, asrLen, asrType, type);
				// 获取总页数
				int pageCount = RokidUtils.getPageCnt(count);
				if(page == null) page = 1;
				if(page > pageCount){
					page = pageCount;
				}
				// 开始数据下标
				int start = (page - 1) * RokidUtils.getPageSize();
				// 用户一栏查询
				List<AsrNlp> listBo = asrNlpDao.list(start, RokidUtils.getPageSize(), userId, sn, startDate, endDate, asrLen, asrType, type);
				for(int i = 0; i < listBo.size(); i++){
					AsrNlpVo vo = new AsrNlpVo();
					AsrNlp bo = listBo.get(i);
					BeanUtil.copyProperties(vo, bo);
					
					// 识别结果修改不为2
					if(vo.getAsrType() == null || vo.getAsrType() != 2){
						vo.setAsrEditCnt(null);
					}
					
					list.add(vo);
				}
				
				// 一栏记录返回
				resMap.put("page", page);
				resMap.put("pageCount", pageCount);
				resMap.put("pageSize", RokidUtils.getPageSize());
				
				// 用户一栏记录返回
				if(page == 1){
					List<IdNameVo> listUser = userMapper.idNameList();					
					resMap.put("listUser", listUser);
				}
			}else{
				
				/**
				 * 查询指派并未完成的指派数据
				 */
				if(getWork != null){
					// 指派数据是否有
					boolean workFlg = false;
					// 指派数据件数
					int ret = 0;
					// 查询指派用户表数据
					List<Work> workList = workMapper.selectWorks(Constants.ASR_TYPE);
					Config configTmp = configMapper.selectByPrimaryKey("dayAsrCnt");
					
					for(int i =0; i < workList.size(); i++){ 
						Work wu = workList.get(i);
						Long startDate = wu.getStartTime();
						Long endDate = wu.getEndTime();
	
						System.out.println("work search for asr ============");
						System.out.println(dateToStrL(new Date(startDate)));
						System.out.println(dateToStrL(new Date(endDate)));
						
						//==============================add======================================				
						if(configTmp == null || configTmp.getValue().equals("0")){

							ret = asrNlpDao.getWorkRecordByWorkId(startDate, endDate, sessionUserId);
		
							//若获取分配数据为0，更新指派用户表，标注为已完成
							if(ret < 50){
								// 更新指派为已完成
								workMapper.updateWorkEndById(Constants.ASR_TYPE, RokidUtils.getSysTime(), wu.getId());				
							}
							
							// 有指派数据
							if(ret > 0){
								workFlg = true;
								break;
							}
						}else{	
							String nowDate = dateToStrS(new Date());
							int dayWorkVer = 1;
							
							//轮训版本号
							WorkVerKey key1 = new WorkVerKey();
							key1.setTime(nowDate);
							key1.setType(Constants.ASR_TYPE);
							WorkVer ver = workVerMapper.selectByPrimaryKey(key1);
							if(ver == null){
								ver = new WorkVer();
								ver.setVer(1);
								ver.setTime(nowDate);
								ver.setType(Constants.ASR_TYPE);
								workVerMapper.insert(ver);
							}else{
								dayWorkVer = ver.getVer();
							}
									
							//按天处理数量
							WorkCntKey key = new WorkCntKey();
							key.setTime(nowDate);
							key.setType(Constants.ASR_TYPE);
							
							long day = (endDate - startDate) / (24 * 3600 * 1000) + 1;
							
							boolean isAllEnd = true;
							boolean loopFlg = false;
							for(int x  = 0; x < day; x ++){
								
								String workDate = dateToStrS(new Date(startDate + x * (24 * 3600 * 1000)));
								key.setWorkDate(workDate);
								WorkCnt workCnt = workCntMapper.selectByPrimaryKey(key);
								int dayWorkCnt = 0;
								
								if(workCnt == null){
									workCnt = new WorkCnt();
									workCnt.setTime(nowDate);
									workCnt.setType(Constants.ASR_TYPE);
									workCnt.setWorkDate(workDate);
									workCnt.setCnt(0);
									workCnt.setAllCnt(0);
									workCnt.setEndTime(null);
									workCntMapper.insert(workCnt);
									isAllEnd = false;
								}else{
									if(workCnt.getEndTime() != null){
										if(i == workList.size() - 1 && x == day -1){
											ver.setVer(1);
											workVerMapper.updateByPrimaryKeySelective(ver);
										}
										if(loopFlg){
											workFlg = true;
											i = -1;
											x = (int)(day);
										}
										continue;
									}else{
										isAllEnd = false;
										dayWorkCnt = workCnt.getCnt();
									}
								}
											
								if(dayWorkCnt >= dayWorkVer * Integer.parseInt(configTmp.getValue())){
									if(i < workList.size() - 1 || x < day - 1){
										loopFlg = true;
										continue;
									}else if(i == workList.size() - 1 || x == day - 1){
										ver.setVer(1);
										workVerMapper.updateByPrimaryKeySelective(ver);
										i = 0;
										continue;
									}
								}
								
								ret = asrNlpDao.getWorkRecordByWorkId(startDate + x * (24 * 3600 * 1000), startDate + (x + 1) * (24 * 3600 * 1000) - 1, sessionUserId);
								workCnt = new WorkCnt();
								workCnt.setTime(nowDate);
								workCnt.setType(Constants.ASR_TYPE);
								workCnt.setWorkDate(workDate);
								if(ret == 0){								
									workCnt.setEndTime(String.valueOf(RokidUtils.getSysTime()));
									workCntMapper.updateByPrimaryKeySelective(workCnt);
								}else{
									loopFlg = true;
									workCnt.setAllCnt(ret);
									workCnt.setCnt(ret);
									workCntMapper.updateByPrimaryKeySelective2(workCnt);
								}
							
								if(ret > 0){
									workFlg = true;
									if(i == workList.size() - 1 && x == day - 1){
										if(dayWorkCnt + ret>= (dayWorkVer * Integer.parseInt(configTmp.getValue()) - 1)){
											ver.setVer(1);
											workVerMapper.updateByPrimaryKeySelective(ver);
										}
									}
									break;
								}else {
									if(x <= day - 1 || i < workList.size() - 1 ){
										continue;			
									}
								}
							}
							
							if(isAllEnd){
								// 更新指派为已完成
								workMapper.updateWorkEndById(Constants.VOICE_TYPE, RokidUtils.getSysTime(), wu.getId());
							}
						}
					}
				
					// 指派数据没有，获取非指派数据
					if(!workFlg){
						ret = asrNlpDao.getWorkRecordByNormal(sessionUserId);
					}					
				}
				
//				/**
//				 * 查询指派并未完成的指派数据
//				 */
//				if(getWork != null){
//					// 指派数据是否有
//					boolean workFlg = false;
//					// 指派数据件数
//					int ret = 0;
//					// 查询指派用户表数据
//					List<Work> workList = workMapper.selectWorks(Constants.ASR_TYPE);
//					for(int i =0; i < workList.size(); i++){
//						Work wu = workList.get(i);
//						Long startDate = wu.getStartTime();
//						Long endDate = wu.getEndTime();
//	
//						System.out.println("work search for asr ============");
//						System.out.println(dateToStrL(new Date(startDate)));
//						System.out.println(dateToStrL(new Date(endDate)));
//						
//						ret = asrNlpDao.getWorkRecordByWorkId(startDate, endDate, sessionUserId);
//	
//						//若获取分配数据为0，更新指派用户表，标注为已完成
//						if(ret < 50){
//							// 更新指派为已完成
//							workMapper.updateWorkEndById(Constants.ASR_TYPE, RokidUtils.getSysTime(), wu.getId());							
//						}
//						
//						// 有指派数据
//						if(ret > 0){
//							workFlg = true;
//							break;
//						}
//					}
//				
//					// 指派数据没有，获取非指派数据
//					if(!workFlg){
//						ret = asrNlpDao.getWorkRecordByNormal(sessionUserId);
//					}
//					
//				}
				
				// 一栏数据查询
				List<AsrNlp> listBo = asrNlpDao.list(null, null, sessionUserId, null, null, null, asrLen, null, null);
				for(int i = 0; i < listBo.size(); i++){
					AsrNlpVo vo = new AsrNlpVo();
					AsrNlp bo = listBo.get(i);
					BeanUtil.copyProperties(vo, bo);				
					list.add(vo);
				}
				
				// 获取标注信息提示
				if(getWork != null){
					if(getWork == list.size()){
						resMap.put("info", "没有最新的标注数据了");
					}else{
						resMap.put("info", "本次新获取到 " + (list.size())  +" 条标注数据");
					}
				}
			}
			
			// 一栏记录返回
			resMap.put("list", list); 
			
			// audio path
			Config configTmp = configMapper.selectByPrimaryKey("asrPath");
			String path = "";//PropertiesUtil.getConfiguration().getString("asrPath");
			if(configTmp != null) path = configTmp.getValue();
			resMap.put("path", path);
			
			// 检查语音文件是否存在
			for(int i = 0; i < list.size(); i++){
				AsrNlpVo vo = list.get(i); 
				if(vo.getFstatus() == null){
					MutliThread m1= new MutliThread(vo.getId(), path + vo.getPath(), null, asrNlpDao);
					m1.start();
				}
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
	public ResponseMap init(RequestMap requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseMap count(RequestMap reqMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseMap updates(RequestMap requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseMap updDomainGroup(RequestMap reqMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseMap selDomainGroup(RequestMap reqMap) {
		// TODO Auto-generated method stub
		return null;
	}	
}
