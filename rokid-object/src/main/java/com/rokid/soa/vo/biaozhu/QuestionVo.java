package com.rokid.soa.vo.biaozhu;

import java.io.Serializable;

public class QuestionVo implements Serializable {
	
	/** ID*/
    private Long id;
   
    private Long time;

    /** filter*/
    private String topic;
    
    /** Assign*/
    private String assignId;
    
    private Long chatId;
    
    private String chatName;
    
	/** 标注者 */
	private String updName;
	
	public String getUpdName() {
		return updName;
	}

	public void setUpdName(String updName) {
		this.updName = updName;
	}

    private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public String getChatName() {
		return chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
}