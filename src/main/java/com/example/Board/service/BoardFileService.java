package com.example.Board.service;

import com.example.Board.entity.Board;
import com.example.Board.entity.BoardFile;
import com.example.Board.repository.BoardFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardFileService {


    private final BoardFileRepository boardFileRepository;

    public Set<BoardFile> getBoards(Long id) {
        return boardFileRepository.findByBoardId(id);
    }

    public Optional<BoardFile> getBoardFile(Long id) {
        return boardFileRepository.findById(id);
    }
    public void setBoardFile(Set<BoardFile> fileList, Board board) {
        for (BoardFile boardFile : fileList) {
            boardFile.setBoard(board);
            boardFileRepository.save(boardFile);
        }
    }
}



