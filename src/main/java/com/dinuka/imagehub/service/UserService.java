package com.dinuka.imagehub.service;

import com.dinuka.imagehub.dto.UserDTO;
import com.dinuka.imagehub.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User save(UserDTO userDTO);

    User update(UserDTO userDTO, Long id);

    String delete(Long id);


}
