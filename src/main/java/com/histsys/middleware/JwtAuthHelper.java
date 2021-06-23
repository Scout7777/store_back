package com.histsys.middleware;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.blade.ioc.annotation.Bean;
import com.histsys.config.Env;
import lombok.Data;

import java.util.Date;

@Bean
public class JwtAuthHelper {
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtAuthHelper() {
        String secret = Env.get("JWT_SECRET", "qwertyuioprtyuidfghjk2345678..1");
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).withIssuer("authUser").build();
    }

    public Algorithm getAlgorithm() {
        return this.algorithm;
    }

    public String token(String payload, Date expiredAt) {
        return JWT.create().withIssuer("authUser").withSubject(payload).withExpiresAt(expiredAt).sign(this.algorithm);
    }

    public String payload(String token) {
        return this.verifier.verify(token).getPayload();
    }

    @Data
    static class UserPayload {
        private String userId;
        private Date expiredAt;
    }

}
