package com.rokid.soa.bo.biaozhu;

import java.io.Serializable;

public class Nlp implements Serializable {
    private String id;

    private String sn;

    private Long time;

    private String asr;
    
    private String path;

    private String domain;

    private String intent;

    private String slot;

    private String newDomain;

    private String newIntent;

    private String newSlot;

    private Short type;

    private String nlp;

    private Long updTime;

    private String updId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr == null ? null : asr.trim();
    }

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent == null ? null : intent.trim();
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot == null ? null : slot.trim();
    }

    public String getNewDomain() {
        return newDomain;
    }

    public void setNewDomain(String newDomain) {
        this.newDomain = newDomain == null ? null : newDomain.trim();
    }

    public String getNewIntent() {
        return newIntent;
    }

    public void setNewIntent(String newIntent) {
        this.newIntent = newIntent == null ? null : newIntent.trim();
    }

    public String getNewSlot() {
        return newSlot;
    }

    public void setNewSlot(String newSlot) {
        this.newSlot = newSlot == null ? null : newSlot.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getNlp() {
        return nlp;
    }

    public void setNlp(String nlp) {
        this.nlp = nlp == null ? null : nlp.trim();
    }

    public Long getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Long updTime) {
        this.updTime = updTime;
    }

    public String getUpdId() {
        return updId;
    }

    public void setUpdId(String updId) {
        this.updId = updId == null ? null : updId.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Nlp other = (Nlp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSn() == null ? other.getSn() == null : this.getSn().equals(other.getSn()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getAsr() == null ? other.getAsr() == null : this.getAsr().equals(other.getAsr()))
            && (this.getDomain() == null ? other.getDomain() == null : this.getDomain().equals(other.getDomain()))
            && (this.getIntent() == null ? other.getIntent() == null : this.getIntent().equals(other.getIntent()))
            && (this.getSlot() == null ? other.getSlot() == null : this.getSlot().equals(other.getSlot()))
            && (this.getNewDomain() == null ? other.getNewDomain() == null : this.getNewDomain().equals(other.getNewDomain()))
            && (this.getNewIntent() == null ? other.getNewIntent() == null : this.getNewIntent().equals(other.getNewIntent()))
            && (this.getNewSlot() == null ? other.getNewSlot() == null : this.getNewSlot().equals(other.getNewSlot()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getNlp() == null ? other.getNlp() == null : this.getNlp().equals(other.getNlp()))
            && (this.getUpdTime() == null ? other.getUpdTime() == null : this.getUpdTime().equals(other.getUpdTime()))
            && (this.getUpdId() == null ? other.getUpdId() == null : this.getUpdId().equals(other.getUpdId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSn() == null) ? 0 : getSn().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getAsr() == null) ? 0 : getAsr().hashCode());
        result = prime * result + ((getDomain() == null) ? 0 : getDomain().hashCode());
        result = prime * result + ((getIntent() == null) ? 0 : getIntent().hashCode());
        result = prime * result + ((getSlot() == null) ? 0 : getSlot().hashCode());
        result = prime * result + ((getNewDomain() == null) ? 0 : getNewDomain().hashCode());
        result = prime * result + ((getNewIntent() == null) ? 0 : getNewIntent().hashCode());
        result = prime * result + ((getNewSlot() == null) ? 0 : getNewSlot().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getNlp() == null) ? 0 : getNlp().hashCode());
        result = prime * result + ((getUpdTime() == null) ? 0 : getUpdTime().hashCode());
        result = prime * result + ((getUpdId() == null) ? 0 : getUpdId().hashCode());
        return result;
    }
}