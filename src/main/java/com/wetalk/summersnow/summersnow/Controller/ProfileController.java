package com.wetalk.summersnow.summersnow.Controller;

import com.wetalk.summersnow.summersnow.dto.PaginationDTO;
import com.wetalk.summersnow.summersnow.mapper.QuestionMapper;
import com.wetalk.summersnow.summersnow.mapper.UserMapper;
import com.wetalk.summersnow.summersnow.model.User;
import com.wetalk.summersnow.summersnow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper usermapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable String action, Model model,
                          HttpServletResponse response, HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

//        String token = "ec1ee71c-d489-48c1-a98b-610a528c99bd";
//        Cookie cookietmp = new Cookie("token", token);
//        User userbyToken = null;
//        response.addCookie(cookietmp);
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        //按照原来的逻辑，如果登录失败，跳转到主页面

        //下面查找用户对应的列表
        if ("questions".equals(action)) {
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的问题");
        }
        if ("replies".equals(action)) {
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的回复");
        }
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination", paginationDTO);
        return "profile";
    }
}
