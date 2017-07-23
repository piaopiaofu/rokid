package com.rokid.soa.bo.biaozhu;

import java.io.Serializable;

public class AsrNlp implements Serializable {
    private String id;

    private String sn;

    private String path;
    
    private Integer fstatus;

    private Long time;

    private String asr;
    
    private String asrEdit;
    
    private Short asrEditCnt;
    
    private Short asrType;

    private String domain;

    private String intent;

    private String slot;
    
    private String nlp;

    private Short type;
    
    private String extField;
    
    private Short editAdmin;

    private Long updTime;

    private String updId;
    
    private String updName;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Integer getFstatus() {
		return fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
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

    public String getNlp() {
		return nlp;
	}

	public void setNlp(String nlp) {
		this.nlp = nlp;
	}

	public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
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
    
    public String getUpdName() {
		return updName;
	}

	public void setUpdName(String updName) {
		this.updName = updName;
	}

    public String getAsrEdit() {
		return asrEdit;
	}

	public void setAsrEdit(String asrEdit) {
		this.asrEdit = asrEdit;
	}

	public Short getAsrEditCnt() {
		return asrEditCnt;
	}

	public void setAsrEditCnt(Short asrEditCnt) {
		this.asrEditCnt = asrEditCnt;
	}

	public Short getAsrType() {
		return asrType;
	}

	public void setAsrType(Short asrType) {
		this.asrType = asrType;
	}

	public String getExtField() {
		return extField;
	}

	public void setExtField(String extField) {
		this.extField = extField;
	}

	public Short getEditAdmin() {
		return editAdmin;
	}

	public void setEditAdmin(Short editAdmin) {
		this.editAdmin = editAdmin;
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
        AsrNlp other = (AsrNlp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSn() == null ? other.getSn() == null : this.getSn().equals(other.getSn()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getAsr() == null ? other.getAsr() == null : this.getAsr().equals(other.getAsr()))
            && (this.getDomain() == null ? other.getDomain() == null : this.getDomain().equals(other.getDomain()))
            && (this.getIntent() == null ? other.getIntent() == null : this.getIntent().equals(other.getIntent()))
            && (this.getSlot() == null ? other.getSlot() == null : this.getSlot().equals(other.getSlot()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getUpdTime() == null ? other.getUpdTime() == null : this.getUpdTime().equals(other.getUpdTime()))
            && (this.getUpdId() == null ? other.getUpdId() == null : this.getUpdId().equals(other.getUpdId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSn() == null) ? 0 : getSn().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getAsr() == null) ? 0 : getAsr().hashCode());
        result = prime * result + ((getDomain() == null) ? 0 : getDomain().hashCode());
        result = prime * result + ((getIntent() == null) ? 0 : getIntent().hashCode());
        result = prime * result + ((getSlot() == null) ? 0 : getSlot().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getUpdTime() == null) ? 0 : getUpdTime().hashCode());
        result = prime * result + ((getUpdId() == null) ? 0 : getUpdId().hashCode());
        return result;
    }
}