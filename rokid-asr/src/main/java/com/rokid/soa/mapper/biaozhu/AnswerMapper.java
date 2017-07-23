package com.rokid.soa.mapper.biaozhu;

import java.util.List;

import com.rokid.soa.bo.biaozhu.Answer;

public interface AnswerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);
    
    List<Answer> listAnswer(Long chatId);
    
    int deleteByChatId(Long chatId);
    
    /**
     * 检查名称是否已存在
     * @param name
     * @param id
     * @return
     */
    int nameCount(String name, Long id);
}