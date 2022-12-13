package com.example.Board.service;

import com.example.Board.controller.UserController;
import com.example.Board.entity.Board;
import com.example.Board.entity.BoardFile;
import com.example.Board.repository.BoardFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BoardFileService {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private BoardFileRepository boardFileRepository;

    public List<BoardFile> selectBoard(Long id) {
        List<BoardFile> boardFile = boardFileRepository.findByBoardId(id);

        return boardFile;
    }

    public Optional<BoardFile> selectBoardFile(Long id) {
        Optional<BoardFile> boardFile = boardFileRepository.findById(id);

        return boardFile;
    }
    public void saveBoardFile(List<BoardFile> fileList, Board board) {

        for (BoardFile boardFile : fileList) {
            boardFile.setBoard(board);
            boardFileRepository.save(boardFile);
        }
    }
}



