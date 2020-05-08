package com.wetalk.summersnow.summersnow.Provider;

import com.alibaba.fastjson.JSON;
import com.wetalk.summersnow.summersnow.dto.AccessTokenInfo;
import com.wetalk.summersnow.summersnow.dto.GithubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Gitprovider {
    @Value("${github.tokenurl}")
    private String tokenurl;
    @Value("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36")
    private String chromeheader;
    @Value("${github.userurl}")
    private String userurl;
    public String getAccessToken (AccessTokenInfo accessTokenInfo){

    MediaType JSONtype = MediaType.get("application/json; charset=utf-8");
    String json  = JSON.toJSONString(accessTokenInfo);
        OkHttpClient client = new OkHttpClient();
        System.out.println(tokenurl);
        RequestBody body = RequestBody.create(JSONtype,json );
            Request request = new Request.Builder()
                    .url(tokenurl)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                //System.out.println("METHOD token before:"+response.body().string());
                String token  = response.body().string();
                System.out.println("METHOD token before:"+token);

                return token;
            }catch (Exception e){
                System.out.println("catch到错误"+e.getStackTrace());
            }
            return null;
    }
    //这个错误是一直存在方法栈里面，所以url在请求第二次的时候会累加成多个url+=userurl+"?access_token="+accessToken;
    public GithubUser getUser (String accessToken) {

        OkHttpClient.Builder clientbuilder = new OkHttpClient.Builder();

        OkHttpClient client = clientbuilder.build();

        String userurltmp = userurl+"?access_token="+accessToken;
        System.out.println(userurltmp);
        //Request.Builder requestBuilder = new Request.Builder().url(requestString).addHeader("apikey","592e46b62cfe201c68bf7d9f18db11ee");
//        Request.Builder builder1= new Request.Builder().url(userurltmp).addHeader("user-agent",chromeheader);
        Request.Builder builder1= new Request.Builder().url(userurltmp);
        Request request = builder1.build();
        try (Response response = client.newCall(request).execute()){
                String infoStr =  response.body().string();
                System.out.println("用户jsno串："+infoStr);
                GithubUser user = JSON.parseObject(infoStr,GithubUser.class);
                return  user;
            }catch (Exception e){
                System.out.println("获取用户信息失败");
                System.out.println(e.getStackTrace() );
                System.out.println(e.getCause() );
            }
             return null;
    }


}
