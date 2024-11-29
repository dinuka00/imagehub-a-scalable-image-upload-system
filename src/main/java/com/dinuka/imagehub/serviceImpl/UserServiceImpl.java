package com.dinuka.imagehub.serviceImpl;

import com.dinuka.imagehub.dto.UserDTO;
import com.dinuka.imagehub.entity.User;
import com.dinuka.imagehub.exceptions.UserNotFoundException;
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
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: "+ id));
    }

    @Override
    public User save(UserDTO user) {

        User existinUser = userRepository.findByEmail(user.getEmail());

        if (existinUser != null) {
            return existinUser;

        }else {

             User newUser = User.builder()
                     .email(user.getEmail())
                     .password(user.getPassword())
                     .firstName(user.getFirstName())
                     .lastName(user.getLastName())
                     .build();

            return userRepository.save(newUser);

        }

    }

    @Override
    public User update(UserDTO user, Long id) {

        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: "+ id));

        if(existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());

            return userRepository.save(existingUser);
        }
        return null;

    }

    @Override
    public String delete(Long id) {

        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: "+ id));

        if(existingUser != null) {
            userRepository.delete(existingUser);

            return "user deleted";
        }
        return null;
    }
}
