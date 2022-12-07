package com.example.Board.service;

import com.example.Board.entity.Comment;
import com.example.Board.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Page<Comment> commentList(Pageable pageable) {
        //기존 List<Comment>값으로 넘어가지만 페이징 설정을 해주면 Page<Comment>로 넘어간다
        return commentRepository.findAll(pageable);
    }
}
