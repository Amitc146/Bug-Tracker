package com.amit.bugtracker.service;

import com.amit.bugtracker.dto.ChartData;
import com.amit.bugtracker.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    User findById(Integer id);

    Set<User> findByName(String name);

    void save(User user);

    void deleteById(Integer id);

    List<User> findAll();

    List<ChartData> getProjectsCount();

}
