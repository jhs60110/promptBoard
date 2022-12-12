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


@Service
public class FileService {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private BoardFileRepository fileRepository;

    public void callFileRepository(List<BoardFile> fileList, Board board) {

        for (BoardFile boardFile : fileList) {
            boardFile.setBoard(board);
            fileRepository.save(boardFile);
        }
    }
}



