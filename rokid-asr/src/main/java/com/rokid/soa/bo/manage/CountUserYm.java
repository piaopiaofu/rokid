package com.rokid.soa.bo.manage;

import java.io.Serializable;

public class CountUserYm implements Serializable {
	
    private Integer voiceCnt;
    
    private Integer voiceErrCnt;

    private Integer asrCnt;
    
    private Integer asrErrCnt;
    
    private Integer chatCnt;
    
    private Integer chatErrCnt;

    private static final long serialVersionUID = 1L;

	public Integer getVoiceCnt() {
		return voiceCnt;
	}

	public void setVoiceCnt(Integer voiceCnt) {
		this.voiceCnt = voiceCnt;
	}

	public Integer getAsrCnt() {
		return asrCnt;
	}

	public void setAsrCnt(Integer asrCnt) {
		this.asrCnt = asrCnt;
	}

	public Integer getChatCnt() {
		return chatCnt;
	}

	public void setChatCnt(Integer chatCnt) {
		this.chatCnt = chatCnt;
	}

	public Integer getVoiceErrCnt() {
		return voiceErrCnt;
	}

	public void setVoiceErrCnt(Integer voiceErrCnt) {
		this.voiceErrCnt = voiceErrCnt;
	}

	public Integer getAsrErrCnt() {
		return asrErrCnt;
	}

	public void setAsrErrCnt(Integer asrErrCnt) {
		this.asrErrCnt = asrErrCnt;
	}

	public Integer getChatErrCnt() {
		return chatErrCnt;
	}

	public void setChatErrCnt(Integer chatErrCnt) {
		this.chatErrCnt = chatErrCnt;
	}
}