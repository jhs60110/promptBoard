package com.example.Board.service;

import com.example.Board.entity.Board;
import com.example.Board.entity.User;
import com.example.Board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void setBoard(Board board, String boardTitle, String boardContent, User authId) {
        board.setTitle(boardTitle);
        board.setContent(boardContent);
        board.setUser(authId);
        boardRepository.save(board);
    }

    public void updateBoard(Board board, String boardId, User authId) {
        board.setUser(authId);
        board.setId(Long.valueOf(boardId));
        boardRepository.save(board);
    }

    public int updateViews(Long id) {
        return this.boardRepository.updateViews(id);
    }

    public Board getBoard(Board board) {
        return boardRepository.findById(board.getId()).get();
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id).get();
    }

    public Board getReferenceById(Long boardId) {
        return boardRepository.getReferenceById(boardId);
    }

    public Page<Board> getBoardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Page<Board> searchBoardList(String SearchKeyword,Pageable pageable){
        return boardRepository.findByTitleContaining(SearchKeyword, pageable);
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
