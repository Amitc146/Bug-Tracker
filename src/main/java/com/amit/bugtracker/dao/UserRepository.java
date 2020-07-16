package com.amit.bugtracker.dao;

import com.amit.bugtracker.dto.ChartData;
import com.amit.bugtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);

    List<User> findAllByFirstNameIsContaining(String name);

    List<User> findAllByLastNameIsContaining(String name);

    List<User> findAllByUserNameIsContaining(String name);

    @Query(nativeQuery = true, value = "SELECT concat(u.first_name, ' ', u.last_name) AS label, count(*) AS value " +
            "FROM user u " +
            "INNER JOIN users_projects ON user_id = u.id " +
            "GROUP BY user_id")
    List<ChartData> getProjectsCount();

}
