package com.rokid.soa.common;

/**
 * 项目名称：OrderService
 * 类名称：Constants
 * 类描述： 通用常量定义
 * 创建时间：2016-05-05
 * 
 * @author fang
 * @version 1.0
 */
public final class Constants {
	
	/** 用户变量名 */
	public static final String SESSION_USER_ID = "sessionUserId";
	public static final String SESSION_USER_TYPE = "sessionUserType";
	public static final String SESSION_USER_NAME = "sessionUserName";

	/** 返回成功消息标志 */
	public static final String SUCCESS = "success";
	/** 返回失败消息标志 */
	public static final String FAIL = "fail";
	/** 标注类型*/
	public static final int VOICE_TYPE = 1;
	public static final int ASR_TYPE = 2;
	public static final int QUESTION_TYPE = 3;
	public static final int CHAT_TYPE = 4;
	public static final int ANSWER_TYPE = 5;
	public static final int ASR_TYPE_new = 6;
}
