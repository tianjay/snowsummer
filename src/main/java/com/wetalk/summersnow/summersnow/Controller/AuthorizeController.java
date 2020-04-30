package com.wetalk.summersnow.summersnow.Controller;

import com.wetalk.summersnow.summersnow.Provider.Gitprovider;
import com.wetalk.summersnow.summersnow.dto.AccessTokenInfo;
import com.wetalk.summersnow.summersnow.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    Gitprovider gitprovider;
    @Value("${github.callbackurl}")
    private String callbackurl;

    @Value("${github.clientid}")
    private String clientid;

    @Value("${github.clientsecret}")
    private String clientsecret;


    @GetMapping("loggercallback")
    public String callback(@RequestParam("code") String code ,
                           @RequestParam("state")String state,
                           Model model){
        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setClient_id(clientid);
        accessTokenInfo.setClient_secret(clientsecret);
        accessTokenInfo.setCode(code);
        accessTokenInfo.setRedirect_uri(callbackurl);
        accessTokenInfo.setState(state);
        String accessToken = gitprovider.getAccessToken(accessTokenInfo);
        System.out.println("controleget:TOKEN="+accessToken);
        accessToken = accessToken.split("&")[0];
        accessToken = accessToken.split("=")[1];
        GithubUser githubUser = gitprovider.getUser(accessToken);
        System.out.println("用户对象："+githubUser);
        model.addAttribute("user",githubUser);
        return "index";
    }

}
