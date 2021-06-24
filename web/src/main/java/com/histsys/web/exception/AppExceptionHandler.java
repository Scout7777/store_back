package com.histsys.web.exception;

import com.histsys.web.http.ResponseBody;
import com.histsys.web.middleware.CurrentUserMethodArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    // Auth
    @ExceptionHandler(value = CurrentUserMethodArgumentResolver.JwtUserDecodeException.class)
    public ResponseEntity exception(CurrentUserMethodArgumentResolver.JwtUserDecodeException exception) {
        return ResponseBody.status(401).message("权限验证失败，请重新登录").toResponseEntity();
    }

    @ExceptionHandler(value = CurrentUserMethodArgumentResolver.JwtUserExpirationException.class)
    public ResponseEntity exception(CurrentUserMethodArgumentResolver.JwtUserExpirationException exception) {
        return ResponseBody.status(401).message("登录过期，请重新登录").toResponseEntity();
    }

    @ExceptionHandler(value = CurrentUserMethodArgumentResolver.JwtUserNoAuthException.class)
    public ResponseEntity exception(CurrentUserMethodArgumentResolver.JwtUserNoAuthException exception) {
        return ResponseBody.status(403).message("无权访问").toResponseEntity();
    }
}
