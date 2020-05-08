package com.wetalk.summersnow.summersnow.dto;

/*
基本属性和question一致，只不过添加了user avataruel的属性值
 */

import com.wetalk.summersnow.summersnow.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;

    private User user;
}
