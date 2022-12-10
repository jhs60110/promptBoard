package com.example.Board.repository;

import com.example.Board.entity.Board;
import com.example.Board.entity.Comment;
import com.example.Board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


}
