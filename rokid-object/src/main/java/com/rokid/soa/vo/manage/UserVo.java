package com.rokid.soa.vo.manage;

import java.io.Serializable;

public class UserVo implements Serializable {

	/** 默认序列号*/
	private static final long serialVersionUID = 1L;
	
	/** id*/
	private String id;
	
	/** 昵称*/
	private String nick;
	
	/** 用户名*/
	private String user;
	
	/** 用户类型*/
	private Short type;
	
	/** 用户类型 */
	private String typeName;
	
	/** 是否有效 */
	private Short isValid;
	
	/** 最后登陆时间*/
	private Long loginTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public Short getIsValid() {
		return isValid;
	}

	public void setIsValid(Short isValid) {
		this.isValid = isValid;
	}	
}