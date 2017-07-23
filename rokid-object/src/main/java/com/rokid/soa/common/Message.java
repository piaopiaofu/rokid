package com.rokid.soa.common;

/**
 * 项目名称：OrderService
 * 类名称：Message
 * 类描述： 消息类
 * 创建时间：2016-05-07
 * 
 * @author fang
 * @version 1.0
 */
public final class Message {
	
	/** 请求验证 */
	public static final String MSG_CHK_000001 = "%s为必须输入项目！";
	
	public static final String MSG_CHK_000002 = "%s格式不正确！";
	
	public static final String MSG_CHK_000003 = "%s长度不正确！";
	
	public static final String MSG_CHK_000004 = "%s长度不能超过%s位！";
	
	/** 消息列表 */
	public static final String MSG_SYS_000001 = "系统异常，请与管理员联系！";
	
	/** 共通消息 */
	public static final String MSG_CS_0000001 = "服务器中断，请联系客服!";
	

	/** 用户消息 */
	public static final String MSG_USER_000001 = "账号或密码输入错误，请重新输入！";
	public static final String MSG_USER_000002 = "数据异常（店铺），请与管理员联系！";
	public static final String MSG_USER_000003 = "数据异常（权限），请与管理员联系！";
	public static final String MSG_USER_000004 = "系统设定异常，请与管理员联系！";
	public static final String MSG_USER_000005 = "数据异常（商品主表），请与管理员联系！";
	public static final String MSG_USER_000006 = "数据异常（店铺集计订货信息），请与管理员联系！";
	public static final String MSG_USER_000007 = "原密码输入错误，请重新输入！";
	public static final String MSG_USER_000008 = "对象权限ID已经存在！";
	public static final String MSG_USER_000009 = "对象权限ID已经被删除！";
	public static final String MSG_USER_000010 = "件订货数据在PROFIT处理失败，请联系IT部";
	public static final String MSG_USER_000011 = "件处理成功";
	public static final String MSG_USER_000012 = "找不到对应的订单号，该条数据处理失败。订单号：";
	public static final String MSG_USER_000013 = "找不到对应的商品信息，该条数据处理失败。货号：";
	public static final String MSG_USER_000014 = "规定时间内未授权 ";
	public static final String MSG_USER_000015 = "系统异常，请与管理员联系！";
	public static final String MSG_USER_000016 = "添加失败，请检查用户是否已存在！";
	public static final String MSG_USER_000017 = "FTP退出异常，请与管理员联系！";
	public static final String MSG_USER_000018 = "FTP上传异常，请与管理员联系！";
	public static final String MSG_USER_000019 = "FTP下载异常，请与管理员联系！";
	public static final String MSG_USER_000020 = "FTP备份异常，请与管理员联系！";
	public static final String MSG_USER_000021 = "FTP连接异常，请与管理员联系！";
}
