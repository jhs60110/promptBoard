package com.example.Board.controller;

import com.example.Board.config.PrincipalDetails;
import com.example.Board.entity.Board;
import com.example.Board.entity.Comment;
import com.example.Board.entity.User;
import com.example.Board.repository.BoardRepository;
import com.example.Board.repository.UserRepository;
import com.example.Board.service.BoardService;
import com.example.Board.service.CommentService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
@Slf4j
public class BoardController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("")
    public String boardList(Authentication authentication, Model model, @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("username", principalDetails.getUser().getUserName());
        }
        Page<Board> list = boardService.boardList(pageable);

        //페이지블럭 처리
        //1을 더해주는 이유는 pageable은 0부터라 1을 처리하려면 1을 더해서 시작해주어야 한다.
        int nowPage = list.getPageable().getPageNumber() + 1;
        //-1값이 들어가는 것을 막기 위해서 max값으로 두 개의 값을 넣고 더 큰 값을 넣어주게 된다.
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "layout/mainPage";
    }

    @GetMapping("/board/save")
    public String boardWrite(Model model, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        model.addAttribute("username", principalDetails.getUsername());

        return "layout/makeBoard";
    }

    @PostMapping("/board/save")
    public String boardSave(Board board, @RequestParam("username") String userName) {
        logger.info("post");
        User authId = userRepository.findByuserName(userName);
        board.setUser(authId);
        boardRepository.save(board);

        return "redirect:/";
    }

    @GetMapping("/board/update")
    public String boardUpdate(Model model, Authentication authentication, Board board) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        model.addAttribute("username", principalDetails.getUser().getUserName());
        logger.info(principalDetails.getUser().getUserName());
        Board boardInfo = boardRepository.findById(board.getId()).get();
        model.addAttribute("boardInfo", boardInfo);

        return "layout/updateBoard";
    }

    @PutMapping("/board/update")
    public String boardUpdate(Board board, @RequestParam("username") String userName, @RequestParam("boardId") String boardId) {
        User authId = userRepository.findByuserName(userName);
        board.setUser(authId);
        board.setId(Long.valueOf(boardId));
        boardRepository.save(board);

        return "redirect:/";
    }

    @GetMapping("/board/{id}")
    public String boardView(Model model, Authentication authentication , @PathVariable Long id) { //pk만 받을거면 pk만
        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("username", principalDetails.getUsername());
            model.addAttribute("userId", principalDetails.getUser().getUserId());
            model.addAttribute("userRole", principalDetails.getUser().getRole());
            //>>
//            model.addAttribute("principalDetails", principalDetails); //??
        }
        Board boardInfo = boardRepository.findById(id).get();
        model.addAttribute("boardInfo", boardInfo);
        logger.info(String.valueOf(boardInfo));

        return "layout/viewBoard";
    }
// 댓글 show
//    @GetMapping("/board/view")
//    public String boardView(Model model, Authentication authentication, Board board, Comment comment, @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        if (authentication != null) {
//            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//            model.addAttribute("username", principalDetails.getUsername());
//            model.addAttribute("userId", principalDetails.getUser().getUserId());
//            model.addAttribute("userRole", principalDetails.getUser().getRole());
//        }
////        if (comment != null) { //그 n번 게시물에 달린 댓글
////            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
////            model.addAttribute("username", principalDetails.getUsername());
////            model.addAttribute("userId", principalDetails.getUser().getUserId());
////            model.addAttribute("userRole", principalDetails.getUser().getRole());
////        }
//
//        Page<Comment> list = commentService.commentList(pageable);
//
//        int nowPage = list.getPageable().getPageNumber() + 1;
//        int startPage = Math.max(nowPage - 4, 1);
//        int endPage = Math.min(nowPage + 9, list.getTotalPages());
//
//        model.addAttribute("list", list);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        Board boardInfo = boardRepository.findById(board.getId()).get();
//        model.addAttribute("boardInfo", boardInfo);
//        logger.info(String.valueOf(boardInfo));
//
//        return "layout/viewBoard";
//    }

    @PostMapping("/board/delete")
    public String boardDelete(@RequestParam("boardId") Long boardId) {
        logger.info("삭제호출");
        logger.info(String.valueOf(boardId));
        boardRepository.deleteById(boardId);

        return "redirect:/";
    }

}