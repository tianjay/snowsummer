package com.wetalk.summersnow.summersnow.Controller;

import com.wetalk.summersnow.summersnow.dto.PaginationDTO;
import com.wetalk.summersnow.summersnow.mapper.QuestionMapper;
import com.wetalk.summersnow.summersnow.mapper.UserMapper;
import com.wetalk.summersnow.summersnow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class IndexControoler {
    @Autowired
    private UserMapper usermapper;
    //        @Autowired
//        private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/")
    public String index(Model model,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size

    ) {

        PaginationDTO paginationDTO = questionService.list(page,size);
        model.addAttribute("pagination", paginationDTO);
        return "index";
    }


}
