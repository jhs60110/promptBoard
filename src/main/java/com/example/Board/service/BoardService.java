package com.example.Board.service;

import com.example.Board.entity.Board;
import com.example.Board.entity.User;
import com.example.Board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void saveBoard(Board board, String boardTitle, String boardContent, User authId) {
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

    public Board selectBoard(Board board) {
        Board boardInfo = boardRepository.findById(board.getId()).get();

        return boardInfo;
    }

    public Board selectBoard(Long id) {
        Board boardInfo = boardRepository.findById(id).get();

        return boardInfo;
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    public Board getReferenceById(Long boardId) {
        Board commentBoardId = boardRepository.getReferenceById(boardId);

        return commentBoardId;
    }

    public Page<Board> boardList(Pageable pageable) {
        //기존 List<Board>값으로 넘어가지만 페이징 설정을 해주면 Page<Board>로 넘어간다

        return boardRepository.findAll(pageable);
    }

}
