package com.wetalk.summersnow.summersnow.interceptor;

import com.wetalk.summersnow.summersnow.mapper.UserMapper;
import com.wetalk.summersnow.summersnow.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
     private UserMapper usermapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //每个请求之前都要验证cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String tokentofind = cookie.getValue();
                    User userbyToken = usermapper.findByToken(tokentofind);
                    if (userbyToken != null) {
                        //可以解决网站重启后，客户端直接登录
                        request.getSession().setAttribute("user", userbyToken);
                    }
                    break;
                }
            }
        }
        return  true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
