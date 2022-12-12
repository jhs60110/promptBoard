package com.example.Board.controller;

import com.example.Board.FileHandler;
import com.example.Board.config.PrincipalDetails;
import com.example.Board.entity.Board;
import com.example.Board.entity.BoardFile;
import com.example.Board.entity.Comment;
import com.example.Board.entity.User;
import com.example.Board.repository.BoardFileRepository;
import com.example.Board.repository.BoardRepository;
import com.example.Board.repository.CommentRepository;
import com.example.Board.repository.UserRepository;

import com.example.Board.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/boards")
public class BoardController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private BoardFileRepository boardFileRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FileHandler fileHandler;

    @GetMapping("/{id}")
    public String boardView(Model model, Authentication authentication, @PathVariable Long id) {
        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("principalDetails", principalDetails);
        }
        Board boardInfo = boardRepository.findById(id).get();
        List<Comment> comment = commentRepository.findByBoardId(id);
        List<BoardFile> boardFile = boardFileRepository.findByBoardId(id);

        model.addAttribute("boardInfo", boardInfo);
        model.addAttribute("comment", comment);
        model.addAttribute("boardFile", boardFile);

        return "layout/board/viewBoard";
    }

    @GetMapping("/new")
    public String boardWrite(Model model, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        model.addAttribute("userName", principalDetails.getUsername()); // principalDetails가 확장하고있는 UserDetails가 수정불가 파일이라 이름을 못바꿈

        return "layout/board/makeBoard";
    }

        @PostMapping("")
        public String boardWrite(@RequestParam("title") String boardTitle, @RequestParam("content") String boardContent, @RequestParam("author") String userName, @RequestPart(value="files",required = false) List<MultipartFile> boardFile, Board board) throws IOException {
        User authId = userRepository.findByUserName(userName);
            board.setTitle(boardTitle);
            board.setContent(boardContent);
            board.setUser(authId);
            boardRepository.save(board);

            if (boardFile != null){
                fileService.callFileRepository(fileHandler.UserFileUpload( boardFile, String.valueOf(authId)), board);
            }


        return "redirect:/";
    }

    @GetMapping("/update/{id}") //TODO 수정은 html단에서 js로 수정칸을 열고 수정 화면을 삭제 한 후 url 주소를 ""로바꾸기
    public String boardUpdate(Model model, Authentication authentication, Board board) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        model.addAttribute("userName", principalDetails.getUser().getUserName());
        Board boardInfo = boardRepository.findById(board.getId()).get();
        model.addAttribute("boardInfo", boardInfo);

        return "layout/board/updateBoard";
    }

    @PutMapping("/update") //TODO 수정은 html단에서 js로 수정칸을 열고 수정 화면을 삭제 한 후 url 주소를 ""로바꾸기
    public String boardUpdate(Board board, @RequestParam("userName") String userName, @RequestParam("boardId") String boardId) {
        User authId = userRepository.findByUserName(userName);
        board.setUser(authId);
        board.setId(Long.valueOf(boardId));
        boardRepository.save(board);

        return "redirect:/";
    }

    @DeleteMapping("")
    public String boardDelete(@RequestParam("boardId") Long boardId) {
        boardRepository.deleteById(boardId);

        return "redirect:/";
    }

}