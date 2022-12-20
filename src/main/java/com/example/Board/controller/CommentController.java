package com.example.Board.controller;

import com.example.Board.entity.Board;
import com.example.Board.entity.Comment;
import com.example.Board.entity.User;
import com.example.Board.service.BoardService;
import com.example.Board.service.CommentService;
import com.example.Board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comments")
public class CommentController {

    private final BoardService boardService;
    private final UserService userService;
    private final CommentService commentService;

    @PostMapping("")
    public String setComment(Comment comment, Authentication authentication, @RequestParam("boardId") Long boardId, HttpServletRequest request) {
        User authId = userService.getUserId(authentication);
        Board commentBoardId = boardService.getReferenceById(boardId);
        commentService.createComment(comment, authId, commentBoardId);

        return "redirect:" + request.getHeader("Referer");
    }

    @PutMapping("/{id}")
    public String updateComment(Comment comment, Authentication authentication, HttpServletRequest request, @PathVariable Long id, @RequestParam("commentUserName") String userName, @RequestParam("boardId") Board boardId) {
        User authId = userService.getUserId(authentication);
        commentService.updateComment(comment, authId, boardId, id);

        return "redirect:" + request.getHeader("Referer");
    }

    @DeleteMapping("/{id}")
    public String deleteComment(HttpServletRequest request, @PathVariable Long id) {
        commentService.deleteComment(id);

        return "redirect:" + request.getHeader("Referer");
    }


}
