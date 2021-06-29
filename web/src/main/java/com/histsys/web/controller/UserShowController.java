package com.histsys.web.controller;

import com.histsys.data.model.User;
import com.histsys.data.repository.UserRepository;
import com.histsys.oauth.annotation.CurrentUser;
import com.histsys.web._view.UserView;
import com.histsys.web.http.ResponseBody;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/users")
public class UserShowController {
    @Resource
    private UserRepository userRepository;

    @GetMapping("/page")
    public ResponseBody page(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBody.ok(userRepository.findAll(PageRequest.of(page, size, Sort.by("staffNo"))).map(UserView::toView));
    }

    @GetMapping("/me")
    public ResponseBody page(@CurrentUser User user) {
        return ResponseBody.ok(UserView.toView(user));
    }
}
