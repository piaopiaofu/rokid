package com.rokid.soa.vo.biaozhu;

import java.io.Serializable;
import java.util.List;

public class ChatVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID*/
    private Long id;
    
    /** 分数*/
    private Float score;
    
    /** 话题*/
    private String name;
    
    /** 问*/
    private String question;
    
    private List<ChatListVo> questionList;

    /** 答*/
    private String answer;
    
    private List<ChatListVo> answerList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<ChatListVo> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<ChatListVo> questionList) {
		this.questionList = questionList;
	}

	public List<ChatListVo> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<ChatListVo> answerList) {
		this.answerList = answerList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}