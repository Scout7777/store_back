package com.histsys.oauth.annotation;

import com.histsys.data.model.User;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
    User.Role[] requiredRoles() default {};

    boolean mustLogin() default true;
}
