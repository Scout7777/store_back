package com.histsys.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.*;
import com.histsys.http.ResponseBody;
import com.histsys.repository.UserRepository;

@Path
public class UserController {
    @Inject
    private UserRepository userRepository;

    @GetRoute("/api/users/:id")
    @JSON
    public ResponseBody get(@PathParam Long id) throws UserRepository.UserNotFoundException {
        return ResponseBody.status(201).body(userRepository.find(id));
    }

    @GetRoute("/api/users")
    @JSON
    public ResponseBody page(@Param(defaultValue = "1") Integer page, @Param(defaultValue = "10") Integer size) {
        return ResponseBody.status(201).body(userRepository.page(page, size));
    }

}
