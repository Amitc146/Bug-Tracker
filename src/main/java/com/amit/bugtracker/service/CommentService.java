package com.amit.bugtracker.service;

import com.amit.bugtracker.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAll();

    Comment findById(Integer id);

    void save(Comment comment);

    void delete(Comment comment);

    void deleteById(Integer id);

}
