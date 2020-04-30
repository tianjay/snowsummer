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

    @Value("${github.userurl}")
    private String userurl;
    public String getAccessToken (AccessTokenInfo accessTokenInfo){

    MediaType JSONtype = MediaType.get("application/json; charset=utf-8");
    String json  = JSON.toJSONString(accessTokenInfo);
        OkHttpClient client = new OkHttpClient();

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
    public GithubUser getUser (String accessToken) {
        OkHttpClient client = new OkHttpClient();

        userurl += "?"+"access_token="+accessToken;
            Request request = new Request.Builder()
                    .url(userurl)
                    .build();

            try (Response response = client.newCall(request).execute()){
                String infoStr =  response.body().string();
                GithubUser user = JSON.parseObject(infoStr,GithubUser.class);
                return  user;
            }catch (Exception e){

            }
        return null;
    }


}
