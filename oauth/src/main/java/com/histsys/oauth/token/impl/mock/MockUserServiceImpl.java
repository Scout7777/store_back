package com.histsys.oauth.token.impl.mock;

import com.histsys.data.model.User;
import com.histsys.data.repository.UserRepository;
import com.histsys.oauth.entity.JwtUser;
import com.histsys.oauth.entity.TokenEntity;
import com.histsys.oauth.token.TokenService;
import com.histsys.oauth.token.impl.jwt.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 使用 Bearer user_1 可替代 jwt 模式登录，若格式无效，则默认 JwtService
 */
@Service("mockUserTokenService")
public class MockUserServiceImpl implements TokenService {
    private UserRepository userRepository;
    private JwtServiceImpl jwtService;

    public MockUserServiceImpl(@Autowired UserRepository userRepository,
                               @Autowired @Qualifier("jwtTokenService") JwtServiceImpl jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public TokenEntity encode(Long userId, String role) {
        return jwtService.encode(userId, role);
    }

    @Override
    public TokenEntity encode(JwtUser jwtUser) {
        return jwtService.encode(jwtUser);
    }

    @Override
    public JwtUser decode(String token) {
        if (token.startsWith("user_")) {
            // proxy it
            String userIdString = token.replaceAll("user_", "").replaceAll(" ", "");
            Long userId = Long.parseLong(userIdString);
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return new JwtUser(user.getId(), user.getRole().name(), false);
            }
        }
        // default
        return jwtService.decode(token);
    }
}
