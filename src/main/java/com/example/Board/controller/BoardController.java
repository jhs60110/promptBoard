package com.example.Board.controller;

import com.example.Board.service.*;
import com.example.Board.config.PrincipalDetails;
import com.example.Board.entity.Board;
import com.example.Board.entity.BoardFile;
import com.example.Board.entity.Comment;
import com.example.Board.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardFileService boardFileService;
    @Autowired
    private BoardFileService fileService;
    @Autowired
    private FileHandler fileHandler;

    @PostMapping("")
    public String saveBoard(@RequestParam("title") String boardTitle, Authentication authentication, @RequestParam("content") String boardContent, @RequestPart(value = "files", required = false) List<MultipartFile> boardFile, Board board) throws IOException {
        User authId = userService.findUserId(authentication);
        boardService.saveBoard(board, boardTitle, boardContent, authId);

        if (boardFile != null) {
            fileService.saveBoardFile(fileHandler.UserFileUpload(boardFile), board);
        }

        return "redirect:/";
    }
    @GetMapping("/{id}")
    public String viewBoard(Model model, Authentication authentication, @PathVariable Long id) {
        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("principalDetails", principalDetails);
            //viewBoard에서 userRole도 쓰고 있어서 한 번에 붙이는게 좋을것같았음
        }

        Board boardInfo = boardService.selectBoard(id);
        boardService.updateViews(boardInfo.getId());
        List<Comment> comment = commentService.selectBoard(id);
        List<BoardFile> boardFile = boardFileService.selectBoard(id);

        model.addAttribute("boardInfo", boardInfo);
        model.addAttribute("comment", comment);
        model.addAttribute("boardFile", boardFile);

        return "layout/board/viewBoard";
    }

    @GetMapping("/new")
    public String saveBoard(Model model, Authentication authentication) {
        String userName = userService.findUserName(authentication);
        model.addAttribute("userName", userName);

        return "layout/board/makeBoard";
    }

    @GetMapping("/update/{id}") //TODO 수정은 html단에서 js로 수정칸을 열고 수정 화면을 삭제 한 후 url 주소를 ""로바꾸기
    public String updateBoard(Model model, Authentication authentication, Board board) {
        String userName = userService.findUserName(authentication);
        Board boardInfo = boardService.selectBoard(board);

        model.addAttribute("userName", userName);
        model.addAttribute("boardInfo", boardInfo);

        return "layout/board/updateBoard";
    }

    @PutMapping("/update") //TODO 수정은 html단에서 js로 수정칸을 열고 수정 화면을 삭제 한 후 url 주소를 ""로바꾸기
    public String updateBoard(Board board, Authentication authentication, @RequestParam("boardId") String boardId) {
        User authId = userService.findUserId(authentication);
        boardService.updateBoard(board, boardId, authId);

        return "redirect:/";
    }

    @DeleteMapping("")
    public String deleteBoard(@RequestParam("boardId") Long boardId) {
        boardService.deleteBoard(boardId);

        return "redirect:/";
    }

}