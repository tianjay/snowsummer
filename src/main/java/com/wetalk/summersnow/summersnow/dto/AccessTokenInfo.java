package com.wetalk.summersnow.summersnow.dto;

import lombok.Data;

@Data
public class AccessTokenInfo {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
