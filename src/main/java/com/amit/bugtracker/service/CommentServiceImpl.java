package com.amit.bugtracker.service;

import com.amit.bugtracker.dao.CommentRepository;
import com.amit.bugtracker.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Integer id) {
        Optional<Comment> result = commentRepository.findById(id);

        Comment comment;
        if (result.isPresent()) {
            comment = result.get();
        } else {
            throw new RuntimeException("Did not find comment id - " + id);
        }

        return comment;
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

}
