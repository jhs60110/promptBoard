package com.example.Board.controller;

import com.example.Board.FileHandler;
import com.example.Board.config.PrincipalDetails;
import com.example.Board.entity.Board;
import com.example.Board.entity.Comment;
import com.example.Board.entity.User;
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
    @Value("${file.path}")
    private String uploadDir;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FileHandler fileHandler;
//    @Autowired
//    private FileRepository fileRepository;

    @GetMapping("/{id}")
    public String boardView(Model model, Authentication authentication, @PathVariable Long id) {
        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("principalDetails", principalDetails);
        }
        Board boardInfo = boardRepository.findById(id).get();
        List<Comment> comment = commentRepository.findByBoardId(id);

        model.addAttribute("comment", comment);
        model.addAttribute("boardInfo", boardInfo);

        return "layout/board/viewBoard";
    }

    @GetMapping("/new")
    public String boardWrite(Model model, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        model.addAttribute("userName", principalDetails.getUsername()); // principalDetails가 확장하고있는 UserDetails가 수정불가 파일이라 이름을 못바꿈

        return "layout/board/makeBoard";
    }

//    @PostMapping("")
//    public String boardWrite(@RequestParam("file") MultipartFile file,  Board board, @RequestParam("boardId") Long boardId, @RequestParam("userName") String userName) throws Exception {
//        User authId = userRepository.findByUserName(userName);
//        board.setUser(authId);
//        boardRepository.save(board);
//        logger.info(String.valueOf(board));
//
//
//
//        if (file != null) {
//            fileService.saveFile(file, boardId);
//
////            for (MultipartFile multipartFile : files) {
////                fileService.saveFile(multipartFile, boardId);
////            }
//
//        }
        @PostMapping("")
        public String boardWrite(@RequestParam("title") String boardTitle, @RequestParam("content") String boardContent, @RequestParam("author") String userName, @RequestPart(value="files",required = false) List<MultipartFile> boardFile, Board board) throws IOException {
        logger.info("호출--------------------------------");
        User authId = userRepository.findByUserName(userName);
            board.setTitle(boardTitle);
            board.setContent(boardContent);
            board.setUser(authId);
            boardRepository.save(board);
            logger.info(String.valueOf(board));

            if (boardFile != null){
                logger.info("파일있다.");
                logger.info(String.valueOf(boardFile.isEmpty()));


                fileService.callFileRepository(fileHandler.UserFileUpload( boardFile, String.valueOf(authId)), board);
            }else{
                logger.info("파일없대");
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