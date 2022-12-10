package com.example.Board.repository;

import com.example.Board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    Comment findByBoardId(Long boardId);
    @Query("select c from Comment c where c.board.id = :id")
    List<Comment> findByBoardId(@Param("id") Long id);

}
