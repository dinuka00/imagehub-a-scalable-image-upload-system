package com.dinuka.imagehub.service;

import com.dinuka.imagehub.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User update(User user, Long id);

    String delete(Long id);
}
