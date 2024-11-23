package com.dinuka.imagehub.serviceImpl;

import com.dinuka.imagehub.dto.UserDTO;
import com.dinuka.imagehub.entity.User;
import com.dinuka.imagehub.repository.UserRepository;
import com.dinuka.imagehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(UserDTO user) {

        //User existinUser = userRepository.findByEmail(user.getEmail());

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

        return userRepository.save(newUser);

//        if (existinUser != null) {
//            return existinUser;
//        }else {
//
//
//        }

    }

    @Override
    public User update(UserDTO user, Long id) {

        User existingUser = userRepository.findById(id).orElse(null);

        if(existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());

            return userRepository.save(existingUser);
        }else{
            return null;
        }

    }

    @Override
    public String delete(Long id) {

        User existingUser = userRepository.findById(id).orElse(null);

        if(existingUser != null) {
            userRepository.delete(existingUser);

            return "user deleted";
        }
        return null;
    }
}
