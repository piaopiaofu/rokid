
package com.rokid.soa.bo.report;

import java.io.Serializable;

public class AsrSexReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private String time;

	private Integer maleok;
	
	private Integer maleerr;
	
	private Integer femaleok;
	
	private Integer femaleerr;
	
	private Integer youngok;
	
	private Integer youngerr;
	
	private Integer maleokidnot;
	
	private Integer maleerridnot;
	
	private Integer femaleokidnot;
	
	private Integer femaleerridnot;
	
	private Integer youngokidnot;
	
	private Integer youngerridnot;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getMaleok() {
		return maleok;
	}

	public void setMaleok(Integer maleok) {
		this.maleok = maleok;
	}

	public Integer getMaleerr() {
		return maleerr;
	}

	public void setMaleerr(Integer maleerr) {
		this.maleerr = maleerr;
	}

	public Integer getFemaleok() {
		return femaleok;
	}

	public void setFemaleok(Integer femaleok) {
		this.femaleok = femaleok;
	}

	public Integer getFemaleerr() {
		return femaleerr;
	}

	public void setFemaleerr(Integer femaleerr) {
		this.femaleerr = femaleerr;
	}

	public Integer getYoungok() {
		return youngok;
	}

	public void setYoungok(Integer youngok) {
		this.youngok = youngok;
	}

	public Integer getYoungerr() {
		return youngerr;
	}

	public void setYoungerr(Integer youngerr) {
		this.youngerr = youngerr;
	}

	public Integer getMaleokidnot() {
		return maleokidnot;
	}

	public void setMaleokidnot(Integer maleokidnot) {
		this.maleokidnot = maleokidnot;
	}

	public Integer getMaleerridnot() {
		return maleerridnot;
	}

	public void setMaleerridnot(Integer maleerridnot) {
		this.maleerridnot = maleerridnot;
	}

	public Integer getFemaleokidnot() {
		return femaleokidnot;
	}

	public void setFemaleokidnot(Integer femaleokidnot) {
		this.femaleokidnot = femaleokidnot;
	}

	public Integer getFemaleerridnot() {
		return femaleerridnot;
	}

	public void setFemaleerridnot(Integer femaleerridnot) {
		this.femaleerridnot = femaleerridnot;
	}

	public Integer getYoungokidnot() {
		return youngokidnot;
	}

	public void setYoungokidnot(Integer youngokidnot) {
		this.youngokidnot = youngokidnot;
	}

	public Integer getYoungerridnot() {
		return youngerridnot;
	}

	public void setYoungerridnot(Integer youngerridnot) {
		this.youngerridnot = youngerridnot;
	}
}