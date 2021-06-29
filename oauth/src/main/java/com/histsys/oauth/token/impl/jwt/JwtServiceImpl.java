package com.histsys.oauth.token.impl.jwt;

import com.histsys.oauth.entity.JwtUser;
import com.histsys.oauth.entity.TokenEntity;
import com.histsys.oauth.token.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Service("jwtTokenService")
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application-oauth[${spring.profiles.active}].properties")
public class JwtServiceImpl implements TokenService {
    private Long expirationTime;
    private Long refreshExpirationTime;
    private SecretKey secretKey; // 生成足够的安全随机密钥，以适合符合规范的签名

    public JwtServiceImpl(@Value("${oauth.token.user.secret_key}") String jwtSecretKey,
                          @Value("${oauth.token.expiration}") Long expirationTime,
                          @Value("${oauth.token.refresh_expiration}") Long refreshExpirationTime) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        this.secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);
        this.expirationTime = expirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    /*  生成登录 TOKEN  */

    @Override
    public TokenEntity encode(Long userId, String role) {
        long expiration = expirationTime == null ? 864000 : expirationTime; // 10 days default
        long refreshExpiration = refreshExpirationTime == null ? 2400000 : refreshExpirationTime; // 30 days default

        TokenEntity entity = new TokenEntity();
        entity.setType("JWT");
        entity.setIssue_provider("HistSys");
        entity.setIssue_date(new Date());
        entity.setAccess_token_expiration_date(new Date(System.currentTimeMillis() + expiration * 1000));
        entity.setRefresh_token_expiration_date(new Date(System.currentTimeMillis() + refreshExpiration * 1000));
        String token = Jwts.builder()
                .setHeaderParam("type", entity.getType())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .claim("role", role)
                .setIssuer(entity.getIssue_provider())
                .setIssuedAt(entity.getIssue_date())
                .setSubject(String.valueOf(userId))
                .setExpiration(entity.getAccess_token_expiration_date())
                .compact();
        String refreshToken = Jwts.builder()
                .setHeaderParam("type", entity.getType())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .claim("role", role)
                .setIssuer(entity.getIssue_provider())
                .setIssuedAt(entity.getIssue_date())
                .setSubject(String.valueOf(userId))
                .setExpiration(entity.getRefresh_token_expiration_date())
                .compact();
        entity.setAccess_token(token);
        entity.setRefresh_token(refreshToken);
        return entity;
    }

    @Override
    public TokenEntity encode(JwtUser jwtUser) {
        if (jwtUser == null) return null;
        return encode(jwtUser.getUserId(), jwtUser.getRole());
    }

    @Override
    public JwtUser decode(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//        List<String> roles = Arrays.stream(((String) claims.get("roles")).split(",")).collect(Collectors.toList());
        Object roleObject = claims.get("role");
        Long userId = Long.parseLong(claims.getSubject());
        Date expiredDate = claims.getExpiration();
        return new JwtUser(userId, roleObject == null ? null : roleObject.toString(), expiredDate.before(new Date()));
    }

}
