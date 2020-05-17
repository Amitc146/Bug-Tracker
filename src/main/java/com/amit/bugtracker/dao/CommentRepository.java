package com.amit.bugtracker.dao;

import com.amit.bugtracker.entity.Comment;
import com.amit.bugtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByUser(User user);

}
