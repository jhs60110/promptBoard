package com.example.Board.controller;

import com.example.Board.service.*;
import com.example.Board.config.PrincipalDetails;
import com.example.Board.entity.Board;
import com.example.Board.entity.BoardFile;
import com.example.Board.entity.Comment;
import com.example.Board.entity.User;
import com.example.Board.repository.BoardFileRepository;
import com.example.Board.repository.BoardRepository;
import com.example.Board.repository.CommentRepository;
import com.example.Board.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(UserController.class);
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

    @GetMapping("/{id}")
    public String viewBoard(Model model, Authentication authentication, @PathVariable Long id) {
        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("principalDetails", principalDetails);
            // TODO 밑에  User authId = userService.selectUser(authentication); 처럼 userName가져와서 붙이기
        }

        Board boardInfo = boardService.selectBoard(id);
        List<Comment> comment = commentService.selectBoard(id);
        List<BoardFile> boardFile = boardFileService.selectBoard(id);

        model.addAttribute("boardInfo", boardInfo);
        model.addAttribute("comment", comment);
        model.addAttribute("boardFile", boardFile);

        return "layout/board/viewBoard";
    }

    @GetMapping("/new")
    public String saveBoard(Model model, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        model.addAttribute("userName", principalDetails.getUsername()); // principalDetails가 확장하고있는 UserDetails가 수정불가 파일이라 이름을 못바꿈
// TODO 밑에  User authId = userService.selectUser(authentication); 처럼 userName가져와서 붙이기
        return "layout/board/makeBoard";
    }

    @PostMapping("")
    public String saveBoard(@RequestParam("title") String boardTitle, Authentication authentication, @RequestParam("content") String boardContent, @RequestPart(value = "files", required = false) List<MultipartFile> boardFile, Board board) throws IOException {
        User authId = userService.selectUser(authentication);
        boardService.saveBoard(board, boardTitle, boardContent, authId);

        if (boardFile != null) {
            fileService.saveBoardFile(fileHandler.UserFileUpload(boardFile, String.valueOf(authId)), board);
        }

        return "redirect:/";
    }

    @GetMapping("/update/{id}") //TODO 수정은 html단에서 js로 수정칸을 열고 수정 화면을 삭제 한 후 url 주소를 ""로바꾸기
    public String updateBoard(Model model, Authentication authentication, Board board) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();// TODO 밑에  User authId = userService.selectUser(authentication); 처럼 userName가져와서 붙이기
        Board boardInfo = boardService.selectBoard(board);

        model.addAttribute("userName", principalDetails.getUser().getUserName());
        model.addAttribute("boardInfo", boardInfo);

        return "layout/board/updateBoard";
    }

    @PutMapping("/update") //TODO 수정은 html단에서 js로 수정칸을 열고 수정 화면을 삭제 한 후 url 주소를 ""로바꾸기
    public String updateBoard(Board board, Authentication authentication, @RequestParam("userName") String userName, @RequestParam("boardId") String boardId) {
        User authId = userService.selectUser(authentication);
        boardService.updateBoard(board, boardId, authId);

        return "redirect:/";
    }

    @DeleteMapping("")
    public String deleteBoard(@RequestParam("boardId") Long boardId) {
        boardService.deleteBoard(boardId);

        return "redirect:/";
    }

}