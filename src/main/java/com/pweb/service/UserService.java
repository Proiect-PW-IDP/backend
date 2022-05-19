package com.pweb.service;

import com.pweb.dao.User;
import com.pweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getById(int id) {
        return userRepository.findById(id).get();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public User save(User user){
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            return userRepository.save(user);
        }

        return user;
    }

    public User delete(int userId){
        User user = getById(userId);
        userRepository.delete(user);
        return user;
    }
}
