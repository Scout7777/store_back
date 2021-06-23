package com.histsys.middleware;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.mvc.RouteContext;
import com.blade.mvc.handler.RouteHandler;
import com.blade.mvc.hook.WebHook;
import com.histsys.http.ResponseBody;
import com.histsys.model.User;
import lombok.Data;

import static io.github.biezhi.anima.Anima.select;

@Bean
public class HttpAuthRouteHandler implements RouteHandler {
    @Inject
    private JwtAuthHelper jwtAuthHelper;

//    private static final String[] whiteList = new String[]{
//            "/api/login",
//            "/api/logout",
//    };
//
//    private boolean inWhiteList(String currentUri) {
//        for (String uri : whiteList) {
//            if (currentUri.startsWith(uri)) {
//                return true;
//            }
//        }
//        return false;
//    }

    private long fetchUserId(String token) throws TokenErrorException, UserNotFoundException {
        String userId = jwtAuthHelper.payload(token);
        if (userId == null || userId.isBlank()) {
            throw new UserNotFoundException(token);
        }
        return Long.parseLong(userId);
    }

    @Override
    public void handle(RouteContext routeContext) {
        // start auth
        String authorization = routeContext.header("authorization");
        if (authorization == null || authorization.isBlank()) {
            routeContext.response().status(401).json(ResponseBody.status(401).message("权限验证失败，请传入身份验证信息"));
            routeContext.abort();
            return;
        }
        String token = authorization.trim().replace("Bearer ", "");
        try {
            Long userId = fetchUserId(token);
            routeContext.session().attribute("userId", userId);
        } catch (TokenErrorException | UserNotFoundException e) {
            routeContext.response().status(401).json(ResponseBody.status(401).message("权限验证失败，无权访问"));
            routeContext.abort();
        }
    }

    static class TokenErrorException extends Exception {
        TokenErrorException(String message) {
            super(message);
        }
    }

    static class UserNotFoundException extends Exception {
        UserNotFoundException(String message) {
            super(message);
        }
    }
}
