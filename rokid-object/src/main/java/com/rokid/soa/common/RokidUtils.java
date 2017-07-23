package com.rokid.soa.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * 项目名称：supplyService
 * 类名称：SupplyUtils
 * 类描述：
 * 创建时间：2016-05-05
 * 
 * @author fang
 * @version 1.0
 */
public class RokidUtils {

	private static int pagesize = 10;

	/**
	 * getGUID(获取32位的GUID)
	 * 
	 * @return String GUID
	 */
	public static String getGUID() {

		UUID uuid = UUID.randomUUID();

		// 获取标准的GUID
		String guid = uuid.toString();

		// 去掉GUID中的"-"
		guid = guid.replace("-", "");

		return guid.toString();
	}
	
	/**
	 * getPageSize(根据记录总条数，获取总页数)
	 * 
	 * @param count 记录总条数
	 * @param numberOfPage 每页数据的记录条数
	 * @return int 总页数
	 */

	public static int getPageSize() {

		return pagesize;
	}

	public static int getPageCnt(int count) {

		int result = 0;

		// 记录条数小于0时
		if (count <= 0) {
			return 1;
		}

		// 计算页数
		result = count / pagesize;

		// 下一页还存在记录时(未满一页)，页数加1
		if (count % pagesize != 0) {
			result++;
		}

		return result;
	}

	/**
	 * getOffset(计算当前页开始的索引)
	 * 
	 * @param currentPage 当前页数
	 * @param numberOfPage 每页数据的记录条数
	 * @return int 分页数据开始的索引
	 */
	public static int getOffset(int currentPage, int pagecount) {

		if (currentPage > pagecount) {
			currentPage = pagecount;
		}
		if (currentPage < 1) {
			currentPage = 1;
		}
		int offset = 0;
		if (currentPage > 0) {
			offset = (currentPage - 1) * pagesize;
		}

		return offset;
	}

	/**
	 * getSysTime(获取系统时间)
	 * 
	 * @return long 系统时间
	 */
	public static long getSysTime() {
		return System.currentTimeMillis();
	}

	/**
	 * long2Date(将long类型时间转换成Date)
	 * 
	 * @param date long类型时间
	 * @return Date 时间
	 */
	public static Date long2Date(long date) {
		return new Date(date);
	}

	/**
	 * NULL转空
	 * @param val
	 * @return
	 */
	public static String nullToEmpty(String val) {
		if (val == null)
			return "";
		return val;
	}

	/**
	 * NULL转0
	 * @param val
	 * @return
	 */
	public static double nullToZero(Object val) {
		if (val == null)
			return 0;
		return Double.valueOf(val.toString());
	}

	/**
	 * 空白验证
	 * @param target
	 * @return
	 */
	public static boolean isBlank(Object target) {
		if (target == null || target.toString().length() == 0 || "".equals(target.toString().trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 非空白验证
	 * @param target
	 * @return
	 */
	public static boolean isNotBlank(Object target) {
		return !isBlank(target);
	}

	/**
	 * 空白验证(空格除外)
	 * @param target
	 * @return
	 */
	public static boolean isEmpty(Object target) {
		if (target == null || target.toString().length() == 0 || "".equals(target.toString())) {
			return true;
		}
		return false;
	}

	/**
	 * BigDecimal
	 * @param target
	 * @return
	 */
	public static BigDecimal defBigDecimal(BigDecimal target) {
		if (target == null) {
			return new BigDecimal("0.00");
		}
		return target;
	}

	/**
	 * Long
	 * @param target
	 * @return
	 */
	public static Long defLong(Long target) {
		if (target == null) {
			return 0l;
		}
		return target;
	}

	/**
	 * 多个参数空白验证(空格除外)
	 * @param args
	 * @return
	 */
	public static boolean isEmpty(Object... args) {
		for (Object target : args) {
			if (isEmpty(target)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 非空白验证(空格除外)
	 * @param target
	 * @return
	 */
	public static boolean isNotEmpty(Object target) {
		return !isEmpty(target);
	}

	/**
	 * 字符串最大长度验证
	 * @param target
	 * @param border
	 * @return
	 */
	public static boolean isUnderMaxLenth(String target, int border) {
		if (isBlank(target)) {
			return true;
		}
		if (target.length() <= border) {
			return true;
		}

		return false;
	}

	/**
	 * 字符串长度一致验证
	 * @param target
	 * @param border
	 */
	public static boolean isLengthEqual(String target, int border) {
		if (isBlank(target)) {
			return false;
		}
		if (target.length() == border) {
			return true;
		}

		return false;
	}

	/**
	 * 整数验证
	 * @param panduan
	 * @param border
	 */
	public static boolean isInteger(String str) {    
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
	    return pattern.matcher(str).matches();    
	}  

	/**
	 * 整数验证
	 * @param panduan
	 * @param border
	 */
	public static String addEmpty(int length,String str) {    
		String empty = "                    ";
		empty = empty.substring(0, length);
		if ("".equals(str) || str == null){
			return empty;
		}else{
			return  empty.substring(0, length -str.length()) + str;
		}
	}  

	/**
	 * 把String转换成BigDecimal
	 * @param panduan
	 * @param border
	 */
	public static BigDecimal String2BigDecimal(String BigDecimalString){
		if (BigDecimalString == null|| "".equals(BigDecimalString.trim())){
			return new BigDecimal("0");
		}
		return new BigDecimal(BigDecimalString);
	}
}
