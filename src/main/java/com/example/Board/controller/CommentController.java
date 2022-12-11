package com.example.Board.controller;

import com.example.Board.config.PrincipalDetails;
import com.example.Board.entity.Board;
import com.example.Board.entity.Comment;
import com.example.Board.entity.User;
import com.example.Board.repository.BoardRepository;
import com.example.Board.repository.CommentRepository;
import com.example.Board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("")
    public String commentWrite(Comment comment, Authentication authentication, @RequestParam("boardId") Long boardId, HttpServletRequest request) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User authId = userRepository.findByUserName(principalDetails.getUsername());
        Board commentBoardId = boardRepository.getReferenceById(boardId);
        comment.setUser(authId);
        comment.setBoard(commentBoardId);
        commentRepository.save(comment);

        return "redirect:" + request.getHeader("Referer");
    }

    @PutMapping("/{id}")
    public String commentUpdate(Comment comment, HttpServletRequest request, @PathVariable Long id, @RequestParam("commentUserName") String userName, @RequestParam("boardId") Board boardId) {
        User authId = userRepository.findByUserName(userName);
        comment.setUser(authId);
        comment.setBoard(boardId);
        comment.setId(id);
        commentRepository.save(comment);

        return "redirect:" + request.getHeader("Referer");
    }

    @DeleteMapping("/{id}")
    public String commentUpdate(Comment comment, HttpServletRequest request, @PathVariable Long id) {
        commentRepository.deleteById(id);

        return "redirect:" + request.getHeader("Referer");
    }
}
