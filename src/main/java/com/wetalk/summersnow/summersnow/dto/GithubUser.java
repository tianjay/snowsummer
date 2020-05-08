package com.wetalk.summersnow.summersnow.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private String id;
    //fastJsnon满足驼峰映射
    private String avatarUrl;
    private String bio;

}
