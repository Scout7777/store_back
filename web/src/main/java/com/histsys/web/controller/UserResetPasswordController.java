package com.histsys.web.controller;

import com.histsys.data.model.User;
import com.histsys.data.repository.UserRepository;
import com.histsys.web.http.ResponseBody;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.histsys.oauth.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/users")
public class UserResetPasswordController {
    @Resource
    private UserRepository userRepository;

    @PostMapping("/password-reset")
    public ResponseEntity resetPw(@CurrentUser User user, @RequestBody Payload payload) {
        String pw = payload.getPassword();
        if (pw == null || pw.isBlank()) {
            return ResponseBody.status(400).message("请传入新密码").toResponseEntity();
        }
        if (pw.length() < 6) {
            return ResponseBody.status(400).message("密码长度过短，请重新设置").toResponseEntity();
        }
        user.setPassword(payload.getPassword());
        userRepository.save(user);
        return ResponseBody.status(200).message("密码重置成功，请重新登录").toResponseEntity();
    }

    @Data
    static class Payload {
        private String password;
    }
}
