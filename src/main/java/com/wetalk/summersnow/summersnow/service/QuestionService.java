package com.wetalk.summersnow.summersnow.service;

import com.wetalk.summersnow.summersnow.dto.PaginationDTO;
import com.wetalk.summersnow.summersnow.dto.QuestionDTO;
import com.wetalk.summersnow.summersnow.mapper.QuestionMapper;
import com.wetalk.summersnow.summersnow.mapper.UserMapper;
import com.wetalk.summersnow.summersnow.model.Question;
import com.wetalk.summersnow.summersnow.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*

 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public  PaginationDTO list(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUser(userId);
        System.out.println(totalCount+":条数据");
        paginationDTO.setPagination(totalCount, page, size);

        if (page < 1) page = 1;
        if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();
        Integer offSet = size * (page - 1);
        List<Question> list = questionMapper.listByUser(userId,offSet, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
//        System.out.println(questionDTOList);
        return paginationDTO;
    }

    public List<QuestionDTO> list() {
        List<Question> list = questionMapper.list1();
        System.out.println(list);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
            System.out.println("这个questionDTO：" + questionDTO.toString());

        }
        System.out.println(questionDTOList);
        return questionDTOList;
    }

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);


        if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();
        if (page < 1) page = 1;
        Integer offSet = size * (page - 1);

        List<Question> list = questionMapper.list(offSet, size);

        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
//        System.out.println(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.getById(id);
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return  questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            question.setGmtModified(question.getGmtCreate());
            questionMapper.upadate(question);
        }
    }
}
