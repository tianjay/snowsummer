package com.wetalk.summersnow.summersnow.Provider;

public class tiny {
    public static void main(String[] args) {
        String s = "access_token=924f73420f500607f14e9c1cf00c031b004bf1ed&scope=user&token_type=bearer";
         s = s.split("&")[0].split("=")[1];
        System.out.println(s);
    }
}
