package com.example.Board.repository;

import com.example.Board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Transactional
    @Modifying
    @Query("update Board b set b.views = b.views + 1 where b.id = :id")
    int updateViews(@Param("id") Long id);
}
