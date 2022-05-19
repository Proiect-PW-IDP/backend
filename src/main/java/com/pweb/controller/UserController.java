package com.pweb.controller;

import com.pweb.dao.User;
import com.pweb.dto.LoginForm;
import com.pweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping
    public ResponseEntity getById(@RequestParam(name = "userId") int userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(userId));
    }

    @GetMapping("/email")
    public ResponseEntity getByEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByEmail(email));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestParam(name = "userId") int userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.delete(userId));
    }
}
