//package com.example.Board.controller.rest;
//
//import com.example.Board.entity.Board;
//import com.example.Board.service.BoardService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/boards")
//public class RestBoardController {
//    private final BoardService boardService;
//
//    @GetMapping("{id}")
//    public Board show(@PathVariable("id") Long id) {
//        Board board = boardService.findWithRels(id);
//        return board;
//    }
//}
