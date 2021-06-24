package com.histsys.oauth.entity;

import lombok.Data;

/**
 * 手机/邮箱等登录提供的验证码服务
 */
@Data
public class LoginCodePair {
    private String ticket;
    private String subject;
    private String code;
    private String time;

    public LoginCodePair(String ticket, String subject, String code, String time) {
        this.ticket = ticket;
        this.subject = subject;
        this.code = code;
        this.time = time;
    }
}
