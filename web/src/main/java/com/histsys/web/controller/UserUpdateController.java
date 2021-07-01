package com.histsys.web.controller;

import com.histsys.data.model.User;
import com.histsys.data.repository.UserRepository;
import com.histsys.web._view.UserView;
import com.histsys.web.http.ResponseBody;
import com.histsys.web.utils.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserUpdateController {
    @Resource
    private UserRepository userRepository;

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,
                                 @RequestBody UserCreateController.Payload payload) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseBody.status(400).message("该用户不存在").toResponseEntity();
        }
        User user = toUserModel(userOptional.get(), payload);
        userRepository.save(user);
        Optional<User> userOptionalSaved = userRepository.findById(id);
        return ResponseBody.status(200).message("更新成功").body(UserView.toView(userOptionalSaved.get())).toResponseEntity();
    }

    private User toUserModel(User user, UserCreateController.Payload payload) {
//        Hospital hospital = hospitalRepository.getOne(payload.getHospitalId());
//        user.setHospital(hospital);
        BeanUtils.copyPropertiesIfNotNull(payload, user.getUserInfo());
        BeanUtils.copyPropertiesIfNotNull(payload, user);
        return user;
    }
}
