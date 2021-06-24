package com.histsys.oauth.entity;

import lombok.Data;

import java.util.List;

/**
 * provide for jwt encode/decode, and controller method parameter
 */
@Data
public class JwtUser {
    private Long userId;
    private String role;
    private boolean isExpiration;
    private boolean available; // 是否登录状态

    public JwtUser(Long userId, String role, boolean isExpiration) {
        this.userId = userId;
        this.role = role;
        this.isExpiration = isExpiration;
    }
    public JwtUser() {
        this.available = false;
        this.isExpiration = false;
    }
}
