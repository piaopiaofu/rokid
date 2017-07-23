package com.rokid.soa.bo.manage;

import java.io.Serializable;

public class WorkCnt extends WorkCntKey implements Serializable {

	private Integer cnt;

    private Integer allCnt;

    private String endTime;

    private static final long serialVersionUID = 1L;

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getAllCnt() {
        return allCnt;
    }

    public void setAllCnt(Integer allCnt) {
        this.allCnt = allCnt;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
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
        WorkCnt other = (WorkCnt) that;
        return (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getWorkDate() == null ? other.getWorkDate() == null : this.getWorkDate().equals(other.getWorkDate()))
            && (this.getCnt() == null ? other.getCnt() == null : this.getCnt().equals(other.getCnt()))
            && (this.getAllCnt() == null ? other.getAllCnt() == null : this.getAllCnt().equals(other.getAllCnt()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getWorkDate() == null) ? 0 : getWorkDate().hashCode());
        result = prime * result + ((getCnt() == null) ? 0 : getCnt().hashCode());
        result = prime * result + ((getAllCnt() == null) ? 0 : getAllCnt().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        return result;
    }
}