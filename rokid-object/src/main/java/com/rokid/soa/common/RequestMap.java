/************************* 版权声明 **********************************
 * 版权所有：Copyright (c) mvgz 2015 
 *
 * 工程名称： retail-core
 * 创建者： guanhuangbai  
 * 创建日期： 2014年9月9日
 * 创建记录： 创建类结构。
 *
 * ************************* 变更记录 ********************************
 * 修改者： 
 * 修改日期：
 * 修改记录：
 *
 **/

package com.rokid.soa.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.lang3.StringUtils; 
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import com.mvgz.soa.utils.validation.ParameterValidator;
import com.mvgz.soa.utils.validation.QuickValidation;

/**
 * 项目名称：supply
 * 类名称：RequestMap
 * 类描述： 请求参数的封装
 * 创建时间：2016-05-05
 * 
 * @author fang
 * @version 1.0
 */
public class RequestMap extends HashMap<String, Object> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//是否必须检查
	public static final boolean MUST_INPUT = true;	
	public static final boolean NOT_MUST_INPUT = false;
    

    
    /**
     * 检查shopId及entityId长度
     * @param name
     * @param val
     */
    public void checkShipEntityLength(String name, String val){
    	if(val == null){
    		throw new BizException(String.format(Message.MSG_CHK_000001, name));
    	}
    	if(val.length() < 8 || val.length() > 32){
    		throw new BizException(String.format(Message.MSG_CHK_000003, name));
    	}
    }
    
    /**
     * 检查shopId及entityId长度
     * @param name
     * @param list
     */
    public void checkShipEntityLength(String name, List<String> list){
    	for(int i = 0; i < list.size(); i++){
			checkShipEntityLength("查询商户", list.get(i));
		}
    }

    /**
     * getStringValue(获取String类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    public String getStringValue(String key) {
        String value = (String) this.get(key);
        
        if (isNullorEmpty(value)) {
            return null;
        }
        
        return value;
    }
    
    /**
     * getGuidValue(获取Boolean类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
	public String getGuidValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			if(value.toString().length() != 32){
				throw new BizException(String.format(Message.MSG_CHK_000003, name));
			}
			return value.toString();
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
	
	/**
     * checkGuidValue(获取Boolean类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
	public String checkGuidValue(String name, String value, boolean mustInput) {
    	
		if (value != null && !value.toString().isEmpty()) {
			if(value.toString().length() != 32){
				throw new BizException(String.format(Message.MSG_CHK_000003, name));
			}
			return value.toString();
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
    
    /**
     * getStringValue(获取String类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
	public String getStringValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			return value.toString();
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
	
	/**
     * getStringValue(获取String类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @param maxLength 最大长度
     * @return name 返回值描述
     */
	public String getStringValue(String name, String key, boolean mustInput, int maxLength) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			if(value.toString().length() > maxLength){
				throw new BizException(String.format(Message.MSG_CHK_000004, new Object[]{name, maxLength}));
			}else{
				return value.toString();
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
    
    /**
     * getBooleanValue(获取Boolean类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    public Boolean getBooleanValue(String key) {
        Object value = this.get(key);
        if (value != null) {
            if (value instanceof Boolean) {
                return (Boolean) value;
            } 
            
            return (Boolean) convert(value, Boolean.class);
        }
        
        return null;
    }
    
    /**
     * getBooleanValue(获取Boolean类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
	public Boolean getBooleanValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				return Boolean.parseBoolean(value.toString());
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }

    /**
     * getShortValue(获取Short类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    public Short getShortValue(String key) {
        Object value = this.get(key);
        if (value != null) {
            if (value instanceof Integer) {
                return ((Integer) value).shortValue();
            } 
            
            return (Short) convert(value, Short.class);
        }

        return null;
    }
    
    /**
     * getShortValue(获取Short类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
	public Short getShortValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				return Short.parseShort(value.toString());
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
	
	/**
     * getByteValue(获取Byte类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
	public Byte getByteValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				return Byte.parseByte(value.toString());
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
   
    /**
     * getIntValue(获取Integer类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    public Integer getIntValue(String key) {
        Object value = this.get(key);
        if (value != null) {
            if (value instanceof Integer) {
                return (Integer) value;
            } 
            
            return (Integer) convert(value, Integer.class);
        }
        
        return null;
    }
    
    /**
     * getIntegerValue(获取Date类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
	public Integer getIntegerValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				return Integer.parseInt(value.toString());
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
	
	/**
     * getTimeValue(获取Time类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
	public Integer getTimeValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				Integer hm = Integer.parseInt(value.toString());
				if(hm >= 0 && hm <= 2359){
					return hm;
				}else{
					throw new BizException(String.format(Message.MSG_CHK_000002, name));
				}
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
    
    /**
     * getLongValue(获取Long类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    public Long getLongValue(String key) {
    	Object value = this.get(key);
        if (value != null) {
            if (value instanceof Long) {
                return (Long) value;
            } 
                
            return (Long) convert(value, Long.class);
        }

        return null;
    }
    
    /**
     * getLongValue(获取Long类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    public Long getLongValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				return Long.parseLong(value.toString());
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
    
    /**
     * getLongDateValue(获取LongDate类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    public Long getLongDateValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				Long val = Long.parseLong(value.toString());
				new Date(val);
				return Long.parseLong(value.toString());
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
    
    /**
     * checkStringMaxLength(检查String长度)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    public Long checkStringMaxLength(String name, String value, int len) {
 
		if (value != null && !value.isEmpty()) {
			if(value.length() > len){
				throw new BizException(String.format(Message.MSG_CHK_000004, new Object[]{name,len}));
			}
		}

        return null;
    }

    /**
     * getFloatValue(获取Float类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    public Float getFloatValue(String key) {
        Object value = this.get(key);
        if (value != null) {
            if (value instanceof Float) {
                return (Float) value;
            } 
                
            return (Float) convert(value, Float.class);
        }

        return null;
    }
    
    /**
     * getFloatValue(获取Float类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
    public Float getFloatValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				return Float.parseFloat(value.toString());
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }

    /**
     * getDoubleValue(获取Double类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     * @Exception 异常对象
     */
    public Double getDoubleValue(String key) {
        Object value = this.get(key);
        if (value != null) {
            if (value instanceof Double) {
                return (Double) value;
            } 
                
            return (Double) convert(value, Double.class);
        }

        return null;
    }
    
    /**
     * getDoubleValue(获取Double类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
    public Double getDoubleValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				return Double.parseDouble(value.toString());
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }

    /**
     * getBigDecimalValue(获取BigDecimal类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     * @Exception 异常对象
     */
    public BigDecimal getBigDecimalValue(String key) {
        Object value = this.get(key);
        if (value != null) {
            if (value instanceof BigDecimal) {
                return (BigDecimal) value;
            } 
                
            return (BigDecimal) convert(value, BigDecimal.class);
        }

        return null;
    }
    
    /**
     * getBigDecimalValue(获取BigDecimal类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
    public BigDecimal getBigDecimalValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				return new BigDecimal(value.toString());
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }

    /**
     * getDateValue(获取Date类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     * @Exception 异常对象
     */
    public Date getDateValue(String key) {
        Object value = this.get(key);
        if (value != null) {
            if (value instanceof Date) {
                return (Date) value;
            } 
            return (Date) convert(value, Date.class);
        }

        return null;
    }
    
    /**
     * getDateValue(获取Date类型的值)
     * @param key 参数描述
     * @param mustInput 是否必须
     * @return name 返回值描述
     */
    @SuppressWarnings("deprecation")
	public Date getDateValue(String name, String key, boolean mustInput) {
    	
		Object value = this.get(key);
		if (value != null && !value.toString().isEmpty()) {
			try{
				return new Date(value.toString());
			}catch(Exception e){
				throw new BizException(String.format(Message.MSG_CHK_000002, name));
			}
		}else if(mustInput){
			throw new BizException(String.format(Message.MSG_CHK_000001, name));
		}

        return null;
    }
    
    /**
     * getValueByType(获取自定义类型的值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    @SuppressWarnings("unchecked")
    public <T> T getValueByType(String key, Class<T> clazz) {
        Map<String, Object> map = (Map<String, Object>) this.get(key);
        if (CollectionUtils.isEmpty(map)) 
            return null;
        return fillObjectwithMap(clazz, map);
    }

    /**
     * 
     * getStringList(获取String类型的集合)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     */
    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key) {
    	Object value = this.get(key);
    	if (value instanceof List) {
            return (List<String>) value;
        }else{
        	
        }
        return new ArrayList<>();
    }

    /**
     * getListByType(获取自定义类型的集合值)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     * @Exception 异常对象
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getListByType(String key, Class<T> clazz) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) this.get(key);
        if (CollectionUtils.isEmpty(list))
            return null;
        List<T> valueList = new ArrayList<>();
        //遍历list集合
        for (Map<String, Object> map : list) {
            valueList.add(fillObjectwithMap(clazz, map));
        }
        return valueList;
    }

    /**
     * fillObjectwithMap(根据map中的键值填充自定义类型的对应属性)
     * 
     * @param name 参数描述
     * @return name 返回值描述
     * @Exception 异常对象
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private <T> T fillObjectwithMap(Class<T> clazz, Map<String, Object> map) {
        //实例化对象
        T object = BeanUtils.instantiateClass(clazz);

        //遍历map集合
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            //排除空键值的数据
            if (!StringUtils.isBlank(entry.getKey()) && entry.getValue() != null) {

                //如果找到field，将值设置进去
                Field field = ReflectionUtils.findField(clazz, entry.getKey());

                if (field != null) {

                    Object fieldValue = null;
                    Object value = entry.getValue();

                    //如果属性值为map,即对象中包含对象
                    if (value instanceof Map) {

                        fieldValue = fillObjectwithMap(field.getType(), (Map<String, Object>) value);

                        //如果属性值为list，即对象中包含集合
                    } else if (value instanceof List) {

                        List listValue = new ArrayList();

                        //遍历list集合
                        for (Object valueObject : (List) value) {

                            //获取field中list里面的类型
                            Class<? extends Object> type = (Class<?>) TypeUtils.getTypeArguments((ParameterizedType) field.getGenericType()).values().toArray()[0];

                            //如果值为map，即为对象时
                            if (valueObject instanceof Map) {
                                listValue.add(fillObjectwithMap(type, (Map<String, Object>) valueObject));
                            } else {
                                valueObject = convert(valueObject, type);
                                
                                if (valueObject != null) {
                                    listValue.add(valueObject);
                                }
                            }
                        }

                        ReflectionUtils.makeAccessible(field);
                        ReflectionUtils.setField(field, object, listValue);
                        continue;

                    } else {
                    	
                    	QuickValidation quickValidation = new QuickValidation();
                        quickValidation.createLocal();
                    	ParameterValidator validationAnnotation = field.getAnnotation(ParameterValidator.class);
                    	if (validationAnnotation != null) {
                            String fieldName = field.getName();
                            Map<String, String[]> action = new HashMap<String, String[]>();
                            Field f = field;
                            f.setAccessible(true);
                            try{
                            	action.put(fieldName, new String[] { value.toString() });
                            }catch(Exception e){
                            	e.printStackTrace();
                            }
                            String msg = quickValidation.validate(action, validationAnnotation);
                            if(msg != null){
                            	throw new BizException(msg);
                            }
                        }
                    	
                        fieldValue = convert(value, field.getType());
                        
                        if (fieldValue == null) {
                            continue;
                        }
                    }

                    //设置field为可访问
                    ReflectionUtils.makeAccessible(field);

                    //设置field的值
                    ReflectionUtils.setField(field, object, fieldValue);
                }
            }
        }
        return object;
    }
    
    /**
     * convert(根据给定的类型进行值的类型转换)  
     *  
     * @param   name  参数描述  
     * @return  name  返回值描述     
     * @Exception 异常对象
     */
    @SuppressWarnings("rawtypes")
    private Object convert(Object value, Class targetType) {
        
        //如果值为字符串类型
        if (value instanceof String) {
            if (value == null || StringUtils.equalsIgnoreCase((String)value, null)) {
                return null;
            }
            
            //如果为日期类型，根据指定日期字符串转换
            if (targetType.equals(Date.class)) {
                return parseStringToDate((String) value);
            }
        }
        
        if (value instanceof Number && targetType.equals(BigDecimal.class)) {
            BigDecimal bd = new BigDecimal(String.valueOf(value));
            value = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            bd = null;
        }
        
        Object returnVal = null;
        
        try {
            returnVal = (new ConvertUtilsBean()).convert(value.toString(), targetType);
        } catch (Exception e) {
        	//throw new BizException("类型错误！");
        	return null;
        }
        
        return returnVal;
    }
    
    /**
     * parseStringToDate(按照定义的日期转换格式将字符串转换为日期)
     * 
     * @param   name  参数描述  
     * @return  name  返回值描述     
     */
    private Date parseStringToDate(String value) {
        if (isNullorEmpty(value)) {
            return null;
        }
        
        try {
            return DateUtils.parseDateStrictly(value, 
                    "yyyy-MM-dd", 
                    "yyyy/MM/dd", 
                    "yyyy/MM/dd HH:mm:ss", 
                    "yyyyMMddHHmmss", 
                    "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            //throw ExceptionHelper.createResponseException(ErrorCode.CS_MSG_000012, e, logger);
        	throw new BizException("日期类型错误！");
        }
    }
    
    /**
     * isNullorEmpty(验证字符串是否为空,当值为null,""," "及"null"等其中之一时，返回true)   
     * 
     * @param   name  参数描述  
     * @return  name  返回值描述     
     */
    private boolean isNullorEmpty(String value) {
        if (StringUtils.isBlank(value) || StringUtils.equalsIgnoreCase(value, null)) {
            return true;
        }
        
        return false;
    }

}
