package com.wetalk.summersnow.summersnow.Controller;

import com.wetalk.summersnow.summersnow.dto.QuestionDTO;
import com.wetalk.summersnow.summersnow.mapper.UserMapper;
import com.wetalk.summersnow.summersnow.model.Question;
import com.wetalk.summersnow.summersnow.model.User;
import com.wetalk.summersnow.summersnow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller

public class PublishController {

    @Autowired
    private UserMapper usermapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {

        QuestionDTO question = questionService.getById(id);
//        model.addAttribute("question",question);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("id", question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            Model model,
            HttpServletRequest request,
            @RequestParam("title") String title, @RequestParam("description") String description,
            @RequestParam("tag") String tag, @RequestParam("id") Integer id) {
        model.addAttribute("title", title);
        model.addAttribute("tag", tag);
        model.addAttribute("description", description);

        if (title == null || title == "") {
            model.addAttribute("error", "title not null");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "description not null");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "tag not null");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "用户未登陆");
            return "publish";
        }
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

}
