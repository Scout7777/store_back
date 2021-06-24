package com.histsys.web.controller;

import com.histsys.data.model.User;
import com.histsys.data.repository.UserRepository;
import com.histsys.oauth.entity.JwtUser;
import com.histsys.oauth.entity.TokenEntity;
import com.histsys.oauth.token.TokenService;
import com.histsys.web.http.ResponseBody;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 账号、密码 换 token
 * <p>
 * /api/login
 */
@RestController
public class LoginController {
    @Resource
    private UserRepository userRepository;
    @Autowired
    @Qualifier("mockUserTokenService")
    private TokenService tokenService;

    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody Payload payload) {
        Optional<User> userOptional = userRepository.findByUid(payload.getUid());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isPasswordCorrect(payload.getPassword())) {
                TokenEntity token = tokenService.encode(new JwtUser(user.getId(), user.getRole().name(), false));
                return ResponseBody.status(200).body(token).toResponseEntity();
            } else {
                return ResponseBody.status(400).message("账号/密码错误").toResponseEntity();
            }
        } else {
            return ResponseBody.status(400).message("用户不存在").toResponseEntity();
        }
    }

    @Data
    static class Payload {
        private String uid;
        private String password;
    }
}
