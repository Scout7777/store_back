package com.histsys.web.middleware;

import com.histsys.data.model.User;
import com.histsys.data.repository.UserRepository;
import com.histsys.oauth.entity.JwtUser;
import com.histsys.oauth.token.TokenService;
import com.histsys.oauth.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Qualifier("mockUserTokenService")
    @Autowired
    private TokenService tokenService;
    @Resource
    private UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return (methodParameter.getParameterType().isAssignableFrom(User.class) && methodParameter.hasParameterAnnotation(CurrentUser.class));
    }


    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        String authorizationHeader = nativeWebRequest.getHeader("Authorization");
        String token = authorizationHeader == null ? null : authorizationHeader.replace("Bearer ", "").trim(); // 'Bearer ' length=7
        // fetch CurrentUser annotation
        CurrentUser currentUser = null;
        Annotation[] methodAnnotations = methodParameter.getParameterAnnotations();
        for (Annotation methodAnnotation : methodAnnotations) {
            if (methodAnnotation instanceof CurrentUser) {
                // 角色检验
                currentUser = (CurrentUser) methodAnnotation;
            }
        }
        // 不管是否 requireLogin 都会进行 token 解析，确保解析出用户信息和角色验证，当 requireLogin false 的时候不抛出异常并将 jwtUser -> available 设为 false
        JwtUser jwtUser = null;
        try {
            jwtUser = tokenService.decode(token);
            if (jwtUser == null) jwtUser = new JwtUser(); else jwtUser.setAvailable(true);
        } catch (Exception e) {
            if (currentUser.mustLogin())
                throw new JwtUserDecodeException(token);
            else {
                jwtUser = new JwtUser();
            }
        }
        if (currentUser.mustLogin() && jwtUser.isExpiration())
            throw new JwtUserExpirationException(token);
        if (currentUser.mustLogin() && !isRoleCheckSuccess(currentUser.requiredRoles(), User.Role.valueOf(jwtUser.getRole()))) {
            throw new JwtUserNoAuthException(currentUser.requiredRoles());
        }
        // select user
        Optional<User> userOptional = userRepository.findById(jwtUser.getUserId());
        return userOptional.orElse(null);
        // return jwtUser;
    }

    private boolean isRoleCheckSuccess(User.Role[] requiredRoles, User.Role userRole) {
        if (requiredRoles.length == 0) return true; // require nothing
        for (User.Role requiredRole : requiredRoles) {
            if (userRole.equals(requiredRole)) {
                return true;
            }
        }
        return false;
    }

    public static class JwtUserDecodeException extends Exception {
        JwtUserDecodeException(String token) {
            super("TOKEN Decode Fail, token: " + token);
        }
    }

    public static class JwtUserExpirationException extends Exception {
        JwtUserExpirationException(String token) {
            super("TOKEN Expiration, token: " + token);
        }
    }

    public static class JwtUserNoAuthException extends Exception {
        JwtUserNoAuthException(User.Role[] requiredRoles) {
            super("Role required: " + Arrays.stream(requiredRoles).map(User.Role::toString).collect(Collectors.joining(",")));
        }
    }
}
