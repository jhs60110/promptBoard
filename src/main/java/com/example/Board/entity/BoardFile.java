package com.example.Board.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;
import lombok.Data;
import javax.persistence.Entity;

@Data
@Entity
@Getter
@Setter
public class BoardFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    private String originalFileName;  // 파일 원본명

    @Column(nullable = false)
    private String filePath;  // 파일 저장 경로

    @Column(nullable = false)
    private String savedFileName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long fileSize;

    @CreationTimestamp
    private Date createdDate;


}
