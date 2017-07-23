package com.rokid.soa.vo.biaozhu;

import java.io.Serializable;

public class ChatListVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID*/
    private Long id;
    
    /** act*/
    private String act;
    
    /** 分数*/
    private String text;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
    
    
}