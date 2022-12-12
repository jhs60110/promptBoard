package com.example.Board.repository;

import com.example.Board.entity.BoardFile;
import com.example.Board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
    @Query("select c from BoardFile c where c.board.id = :id")
    List<BoardFile> findByBoardId(@Param("id") Long id);
}
