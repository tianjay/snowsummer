package com.wetalk.summersnow.summersnow.Controller;

import com.wetalk.summersnow.summersnow.dto.QuestionDTO;
import com.wetalk.summersnow.summersnow.mapper.QuestionMapper;
import com.wetalk.summersnow.summersnow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id,
                           Model model){
        QuestionDTO questionDTO =  questionService.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }

}
