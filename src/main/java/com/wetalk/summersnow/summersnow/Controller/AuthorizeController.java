package com.wetalk.summersnow.summersnow.Controller;

import com.wetalk.summersnow.summersnow.Provider.Gitprovider;
import com.wetalk.summersnow.summersnow.dto.AccessTokenInfo;
import com.wetalk.summersnow.summersnow.dto.GithubUser;
import com.wetalk.summersnow.summersnow.mapper.UserMapper;
import com.wetalk.summersnow.summersnow.model.User;
import com.wetalk.summersnow.summersnow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    Gitprovider gitprovider;
    @Value("${github.callbackurl}")
    private String callbackurl;

    @Value("${github.clientid}")
    private String clientid;

    @Value("${github.clientsecret}")
    private String clientsecret;

    @GetMapping("loggercallback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model) {
        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setClient_id(clientid);
        accessTokenInfo.setClient_secret(clientsecret);
        accessTokenInfo.setCode(code);
        accessTokenInfo.setRedirect_uri(callbackurl);
        accessTokenInfo.setState(state);
        String accessToken = gitprovider.getAccessToken(accessTokenInfo);
        System.out.println("controleget:TOKEN=" + accessToken);
        accessToken = accessToken.split("&")[0];
        accessToken = accessToken.split("=")[1];
        GithubUser githubUser = gitprovider.getUser(accessToken);
        System.out.println("用户对象：" + githubUser);


        if (githubUser != null && githubUser.getName() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());

            user.setAccountId(githubUser.getId());

            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);

            //插入过后 把cookie放在客户端，，内容为一个token:uuid,这个token同时在用户表
            //这个token在页面跳转到主页后，会进行数据库查询和验证
            Cookie cookie = new Cookie("token", token);
            //实际上github登录成功过后就做了一件事，把该token的user放进数据库,放在session,所以登录不成功就写一个假的数据库内存在的token假放进去浏览器
            response.addCookie(cookie);
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        } else
            model.addAttribute("user", githubUser);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect：/";
    }


}
