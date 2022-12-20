package com.example.Board.service;

import com.example.Board.entity.Board;
import com.example.Board.entity.Comment;
import com.example.Board.entity.User;
import com.example.Board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;

    public void createComment(Comment comment, User authId, Board commentBoardId) {
        comment.setUser(authId);
        comment.setBoard(commentBoardId);
        commentRepository.save(comment);
    }

    public void updateComment(Comment comment, User authId, Board boardId, Long id) {
        comment.setId(id);
        comment.setUser(authId);
        comment.setBoard(boardId);
        commentRepository.save(comment);
    }

    public Set<Comment> getBoards(Long id) {
        return commentRepository.findByBoardId(id);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
