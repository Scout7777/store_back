package com.histsys.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PostRoute;
import com.histsys.config.Env;
import com.histsys.http.ResponseBody;
import com.histsys.middleware.JwtAuthHelper;
import com.histsys.model.User;
import com.histsys.repository.UserRepository;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 账号、密码 换 token
 */
@Path
public class LoginController {
    @Inject
    private UserRepository userRepository;
    @Inject
    private JwtAuthHelper jwtAuthHelper;
    private long expiredMillis = 1000 * 60 * 60 * 24 * 10;

    public LoginController() {
        String expiredDayStr = Env.get("JWT_EXPIRE_TIME", "10");
        int day = Integer.parseInt(expiredDayStr);
        this.expiredMillis = 1000 * 60 * 60 * 24 * day;
    }

    @PostRoute("/api/login")
    @JSON
    public ResponseBody login(Payload payload) {
        try {
            User user = userRepository.find(payload.getUid());
            if (user.isPasswordCorrect(payload.getPassword())) {
                Date expiredAt = this.expiredAt();
                String token = jwtAuthHelper.token("" + user.getId(), expiredAt);
                Map<String, String> map = new HashMap<>();
                map.put("token", token);
                map.put("expiredAt", expiredAt.toString());
                return ResponseBody.status(200).body(map);
            } else {
                return ResponseBody.status(400).message("密码错误");
            }
        } catch (UserRepository.UserNotFoundException e) {
            return ResponseBody.status(404).message("用户不存在");
        }
    }

    // 默认十天
    private Date expiredAt() {
        return new Date(System.currentTimeMillis() + this.expiredMillis);
    }

    @Data
    static class Payload {
        private String uid;
        private String password;
    }
}
