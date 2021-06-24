package com.histsys.oauth.annotation;

import com.histsys.data.model.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 角色权限检验，满足其一即可放行
 */

@Target({ElementType.METHOD})
public @interface AuthRequired {
    User.Role[] value();
}
