package com.rokid.soa.dao.common;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommDao {
	
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	/* 数据库操作类型 **/
	private static final String INSERT = "insert";
	private static final String INSERT_SELECTIVE = "insertSelective";
	private static final String UPDATE_BY_KEY = "updateByPrimaryKey";
	private static final String UPDATE_SELECTIVE = "updateByPrimaryKeySelective";
	private static final String SELECT_BY_PRM_KEY = "selectByPrimaryKey";
	private static final String DELETE_BY_PRM_KEY = "deleteByPrimaryKey";
	
//	@Autowired
//    protected ICacheService cacheService;

	/**
	 * 获取MapperXML路径 
	 * @param record
	 * @param opt
	 * @return
	 */
	private String getNameSpacePath(Object record, String opt){
    	return record.getClass().getName().toString().replace(".bo.", ".mapper.") + "Mapper." + opt;
    }
    
//    /**
//     * 删除REDIS值
//     * @param record
//     * @throws Exception
//     */
//	public void delRedisKey(Object record) throws Exception{
//    	String json = JsonUtil.beanToJson(record);
//    	JsonNode node = JsonUtil.complexJson(json);
//    	String id = node.findValue("id").getTextValue();
//    	cacheService.del(record.getClass().getSimpleName() + id);
//    }
	
//	/**
//     * 删除REDIS值
//     * @param id
//     * @throws Exception
//     */
//	public void delRedisKey(String id){
//    	cacheService.del(RedisConstants.REDIS_KEY + id);
//    }
//	
//	/**
//     * 删除REDIS值
//     * @param idList
//     * @throws Exception
//     */
//	public void delRedisKey(List<String> idList) {
//
//        String[] ids = (String[])idList.toArray(new String[idList.size()]);
//
//		for (int i = 0; i < ids.length; i++) {
//
//			ids[i] = RedisConstants.REDIS_KEY + ids[i];
//		}
//
//    	cacheService.del(ids);
//    }
    
//    /**
//     * 添加REDISkey值
//     * @param record
//     * @throws Exception
//     */
//	public void addRedisKey(Object record, String id) throws Exception{
//    	String json = JsonUtil.beanToJson(record);
//    	cacheService.set(record.getClass().getSimpleName() + id, json);
//    }
    
    /**
     * 根据KEY查询记录
     * @param id
     * @param record
     * @return
     * @throws Exception
     */
    public Object selectByPrimaryKey(Object record, String id) throws Exception {
    	
//    	if(id == null || id.isEmpty()) return null;
//
//    	String json = cacheService.get(record.getClass().getSimpleName() + id);
//    	Object rd = null;
//    	
//    	if(json == null){
//	    	rd = sqlSessionTemplate.selectOne(getNameSpacePath(record, SELECT_BY_PRM_KEY), id);
//	    	if(rd != null) addRedisKey(rd, id);
//    	}else{
//    		rd = JsonUtil.jsonToBean(json, record.getClass());
//    	}
    	Object rd = sqlSessionTemplate.selectOne(getNameSpacePath(record, SELECT_BY_PRM_KEY), id);
       	return rd;
	}
  
    /**
     * 添加记录
     * @param record
     * @return
     * @throws Exception
     */
	public int insert(Object record) throws Exception {
        int rows = sqlSessionTemplate.insert(getNameSpacePath(record, INSERT), record);
        return rows;
	}
	
	/**
	 * 添加记录
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public int insertSelective(Object record) throws Exception {
        int rows = sqlSessionTemplate.insert(getNameSpacePath(record, INSERT_SELECTIVE), record);
        return rows;
	}
	
	/**
	 * 根据KEY更新记录
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public int updateByPrimaryKey(Object record) throws Exception {
        int rows = sqlSessionTemplate.update(getNameSpacePath(record, UPDATE_BY_KEY), record);
//        if (rows > 0 ) {
//        	delRedisKey(record);
//        }
        return rows;
	}
	
	/**
	 * 根据KEY更新记录
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public int updateByPrimaryKeySelective(Object record) throws Exception {
        int rows = sqlSessionTemplate.update(getNameSpacePath(record, UPDATE_SELECTIVE), record);
//        if (rows > 0 ) {
//        	delRedisKey(record);
//        }
        return rows;
	}
	/**
	 * 根据KEY删除记录
	 * @param record
	 * @return   deleteByPrimaryKey
	 * @throws Exception
	 */
	public int deleteByPrimaryKeySelective(Object record) throws Exception {
        int rows = sqlSessionTemplate.delete(getNameSpacePath(record, DELETE_BY_PRM_KEY), record);
//        if (rows > 0 ) {
//        	delRedisKey(record);
//        }
        return rows;
	}
}