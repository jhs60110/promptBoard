package com.example.Board.repository;

import com.example.Board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    @Query("select c from Comment c where c.board.id = :id")
    List<Comment> findByBoardId(@Param("id") Long id);

}
