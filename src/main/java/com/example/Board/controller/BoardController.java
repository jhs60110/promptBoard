package com.example.Board.controller;

import com.example.Board.config.PrincipalDetails;
import com.example.Board.entity.Board;
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
//@RequiredArgsConstructor
@RequestMapping("/boards")
//@Slf4j
public class BoardController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping({"", "/list"})
    public String boardMain(Authentication authentication, Model model, @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("UserDetails", principalDetails.getUser().getUserName());
        }
        Page<Board> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "layout/board/mainPage";
    }

    //게사글 상세보기
    @GetMapping("/{id}")
    public String boardView(Model model, Authentication authentication, @PathVariable Long id) { //pk만 받을거면 pk만 board로 다 받아오면 뭐하는 앤지 유추하기 어렵잖아!
        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            model.addAttribute("principalDetails", principalDetails);
        }

        Board boardInfo = boardRepository.findById(id).get();
        model.addAttribute("boardInfo", boardInfo);

        return "layout/board/viewBoard";
    }

    @GetMapping("/new")
    public String boardWrite(Model model, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        model.addAttribute("userName", principalDetails.getUsername()); // principalDetails가 확장하고있는 UserDetails가 수정불가 파일이라 이름을 못바꿈

        return "layout/board/makeBoard";
    }

    @PostMapping("/new")
    public String boardWrite(Board board, @RequestParam("userName") String userName) {
        User authId = userRepository.findByuserName(userName);
        board.setUser(authId);
        boardRepository.save(board);

        return "redirect:/boards";
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
        User authId = userRepository.findByuserName(userName);
        board.setUser(authId);
        board.setId(Long.valueOf(boardId));
        boardRepository.save(board);

        return "redirect:/boards";
    }


    @DeleteMapping("")
    public String boardDelete(@RequestParam("boardId") Long boardId) {
        boardRepository.deleteById(boardId);

        return "redirect:/boards";
    }

}