package com.histsys.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PathParam;
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
}
