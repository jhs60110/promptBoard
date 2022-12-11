package com.example.Board.repository;

import com.example.Board.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<BoardFile, Long> {
}
