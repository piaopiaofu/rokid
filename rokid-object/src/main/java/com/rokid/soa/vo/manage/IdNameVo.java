package com.rokid.soa.vo.manage;

import java.io.Serializable;

public class IdNameVo implements Serializable {

	/** 默认序列号*/
	private static final long serialVersionUID = 1L;
	
	/** id*/
	private String id;
	
	/** var*/
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}