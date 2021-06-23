package com.histsys.repository.impl;

import com.blade.ioc.annotation.Bean;
import com.histsys.model.User;
import com.histsys.repository.UserRepository;
import io.github.biezhi.anima.page.Page;

import static io.github.biezhi.anima.Anima.select;

@Bean
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Page<User> page(int page, int size) {
        return select().from(User.class).page(page, size);
    }

    @Override
    public User find(long userId) throws UserNotFoundException {
        User user = select().from(User.class).where("id=?", userId).one();
        if (user == null) {
            throw new UserNotFoundException(String.format("User: [%s] not found", "" + userId));
        }
        return user;
    }

    @Override
    public User find(String uid) throws UserNotFoundException {
        User user = select().from(User.class).where("uid=?", uid).one();
        if (user == null) {
            throw new UserNotFoundException(String.format("User: [%s] not found", uid));
        }
        return user;
    }
}
