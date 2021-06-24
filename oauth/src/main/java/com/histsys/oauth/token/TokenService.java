package com.histsys.oauth.token;

import com.histsys.oauth.entity.JwtUser;
import com.histsys.oauth.entity.TokenEntity;

public interface TokenService {

    /* TOKEN for user auth */

    TokenEntity encode(Long userId, String role);

    TokenEntity encode(JwtUser jwtUser);

    JwtUser decode(String token);

}
