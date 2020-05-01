package com.amit.bugtracker.service;

import com.amit.bugtracker.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    User findById(Long id);

    void save(User user);

    void deleteById(Long id);

    List<User> findAll();

}
