package com.example.Board.repository;

import com.example.Board.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
    @Transactional
    @Query("select c from BoardFile c where c.board.id = :id")
    List<BoardFile> findByBoardId(@Param("id") Long id);
}
