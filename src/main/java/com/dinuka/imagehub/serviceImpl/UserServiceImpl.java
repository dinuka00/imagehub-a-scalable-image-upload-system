package com.dinuka.imagehub.serviceImpl;

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
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {

        User existingUser = userRepository.findById(user.getId()).orElse(null);

        if(existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());

            return userRepository.save(existingUser);
        }else{
            return null;
        }

    }

    @Override
    public void delete(Long id) {

    }
}
