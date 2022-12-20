package com.example.Board.controller;

import com.example.Board.service.*;
import com.example.Board.config.PrincipalDetails;
import com.example.Board.entity.Board;
import com.example.Board.entity.BoardFile;
import com.example.Board.entity.Comment;
import com.example.Board.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/boards")
public class BoardController {

    private final UserService userService;

    private final CommentService commentService;
    private final BoardService boardService;

    private final BoardFileService boardFileService;

    private final BoardFileService fileService;

    private final FileHandler fileHandler;

    @PostMapping("")
    public String create(@ModelAttribute Board board,
                         Authentication authentication) throws IOException {
        User authId = userService.getUserId(authentication);

        return "redirect:/";
    }

    @GetMapping("/new")
    public String setBoard(Model model, Authentication authentication) {
        String userName = userService.getUserName(authentication);
        model.addAttribute("userName", userName);

        return "layout/board/makeBoard";
    }

    @GetMapping("/{id}")
    public String find(Model model, Authentication authentication, @PathVariable Long id) {
        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("principalDetails", principalDetails);
            //viewBoard에서 userRole도 쓰고 있어서 한 번에 붙이는게 좋을것같았음
        }

        Board boardInfo = boardService.findWithRels(id);


        model.addAttribute("boardInfo", boardInfo);

        //join

        return "layout/board/viewBoard";
    }


    @GetMapping("/update/{id}") //TODO 수정은 html단에서 js로 수정칸을 열고 수정 화면을 삭제 한 후 url 주소를 ""로바꾸기
    public String updateBoard(Model model, Authentication authentication, Board board) {
        String userName = userService.getUserName(authentication);
        Board boardInfo = boardService.getBoard(board);

        model.addAttribute("userName", userName);
        model.addAttribute("boardInfo", boardInfo);

        return "layout/board/updateBoard";
    }

    @PutMapping("/update") //TODO 수정은 html단에서 js로 수정칸을 열고 수정 화면을 삭제 한 후 url 주소를 ""로바꾸기
    public String updateBoard(Board board, Authentication authentication, @RequestParam("boardId") String boardId) {
        User authId = userService.getUserId(authentication);
        boardService.updateBoard(board, boardId, authId);

        return "redirect:/";
    }

    @DeleteMapping("")
    public String deleteBoard(@RequestParam("boardId") Long boardId) {
        boardService.deleteBoard(boardId);

        return "redirect:/";
    }

}