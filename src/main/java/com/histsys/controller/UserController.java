package com.histsys.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.*;
import com.histsys.http.ResponseBody;
import com.histsys.repository.UserRepository;

@Path
public class UserController {
    @Inject
    private UserRepository userRepository;

    @GetRoute("/users/:id")
    @JSON
    public ResponseBody get(@PathParam Long id) {
        return ResponseBody.status(201).body(userRepository.find(id));
    }

    @GetRoute("/users")
    @JSON
    public ResponseBody page(@Param(defaultValue = "1") Integer page, @Param(defaultValue = "10") Integer size) {
        return ResponseBody.status(201).body(userRepository.page(page, size));
    }

}
