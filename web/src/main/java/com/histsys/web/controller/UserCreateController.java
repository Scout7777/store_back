package com.histsys.web.controller;

import com.histsys.data.model.User;
import com.histsys.data.model.UserInfo;
import com.histsys.data.pojo.FileInfo;
import com.histsys.data.repository.UserInfoRepository;
import com.histsys.data.repository.UserRepository;
import com.histsys.web._view.UserView;
import com.histsys.web.http.ResponseBody;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserCreateController {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserInfoRepository userInfoRepository;


    @PostMapping
    public ResponseEntity create(@RequestBody Payload payload) {
        // 检查数据合法
//        if (payload.getHospitalId() == null) {
//            return ResponseBody.status(400).message("请选择所属医院").toResponseEntity();
//        }
        if (payload.getPassword() == null || payload.getPassword().isBlank()) {
            return ResponseBody.status(400).message("请设置初始密码").toResponseEntity();
        }
        if (payload.getStaffNo() != null && !payload.getStaffNo().isBlank()) {
            Optional<User> user = userRepository.findByStaffNo(payload.getStaffNo());
            if (user.isPresent()) {
                return ResponseBody.status(400).message("该工号已被注册").toResponseEntity();
            }
        }
        // create pojo
        User user = toUserModel(payload);
        user.setStatus(User.Status.active);
        // staffNo: 如果未传入，则设置一个
        boolean needResetStaffNo = false;
        if (user.getStaffNo() == null || user.getStaffNo().isBlank()) {
            user.setStaffNo(null);
            needResetStaffNo = true;
        }
        user = userRepository.save(user);
        if (needResetStaffNo) {
            user.setStaffNo("" + (10000 + user.getId()));
        }
        // save user_info
        UserInfo userInfo = toUserInfoModel(user, payload);
        userInfo = userInfoRepository.save(userInfo);
        user.setUserInfo(userInfo);
        userRepository.save(user);
        // reload and return
        Optional<User> result = userRepository.findById(user.getId());
        UserView userView = UserView.toView(result.get());
        return ResponseBody.status(201).body(userView).toResponseEntity();
    }


    private User toUserModel(Payload payload) {
//        Hospital hospital = hospitalRepository.getOne(payload.getHospitalId());

        User user = new User();

        user.setId(null);
//        user.setHospital(hospital);
        user.setRole(payload.getRole());
        user.setStaffNo(payload.getStaffNo());
        user.setAvatar(payload.getAvatar());
        user.setName(payload.getName());
        user.setEmail(payload.getEmail());
        user.setTelephone(payload.getTelephone());
        user.setPassword(payload.getPassword()); // 会自动hash+salt

        user.setStatus(payload.getStatus());
        return user;
    }

    private UserInfo toUserInfoModel(User user, Payload payload) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());

        userInfo.setIdNo(payload.getIdNo());
        userInfo.setIdType(payload.getIdType());


        return userInfo;
    }


    @Data
    static class Payload {
        private String hospitalId;
        private User.Role role;
        private String staffNo;
        private String avatar;
        private String name;
        private String email;
        private String telephone;
        private String password;

        private User.Status status;

        // user info
        private String idNo; // 身份证
        private String idType;



    }
}
