package com.dinuka.imagehub.controller;

import com.dinuka.imagehub.dto.UserDTO;
import com.dinuka.imagehub.entity.User;
import com.dinuka.imagehub.exceptions.UserNotFoundException;
import com.dinuka.imagehub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO user, @PathVariable Long id) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.update(user, id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.delete(id));

    }

    @GetMapping("/test-runtime")
    public void testRuntime() {
        throw new RuntimeException("Testing runtime exception");
    }

}
