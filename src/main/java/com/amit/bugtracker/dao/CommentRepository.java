package com.amit.bugtracker.dao;

import com.amit.bugtracker.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
