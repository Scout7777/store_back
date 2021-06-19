package com.histsys.repository;

import com.histsys.model.User;
import io.github.biezhi.anima.page.Page;

public interface UserRepository {
    Page<User> page(int page, int size);

    User find(long userId);
}
