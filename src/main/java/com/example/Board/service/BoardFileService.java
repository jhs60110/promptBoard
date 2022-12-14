package com.example.Board.service;

import com.example.Board.entity.Board;
import com.example.Board.entity.BoardFile;
import com.example.Board.repository.BoardFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BoardFileService {

    @Autowired
    private BoardFileRepository boardFileRepository;

    public List<BoardFile> getBoards(Long id) {
        return boardFileRepository.findByBoardId(id);
    }

    public Optional<BoardFile> getBoardFile(Long id) {
        return boardFileRepository.findById(id);
    }
    public void setBoardFile(List<BoardFile> fileList, Board board) {
        for (BoardFile boardFile : fileList) {
            boardFile.setBoard(board);
            boardFileRepository.save(boardFile);
        }
    }
}



