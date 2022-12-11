package com.example.Board.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.*;

@Entity  //jpa가 관리한다.
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    private String userId;

    @OneToMany(mappedBy = "user") //어느 테이블에 매핑되는지
    private List<Board> board = new ArrayList<>();

    @OneToMany(mappedBy = "user") //어느 테이블에 매핑되는지
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "user") //어느 테이블에 매핑되는지
    private List<BoardFile> boardFile = new ArrayList<>();

    @Column(nullable = false, length = 10)
    private String userName;

    @Column(nullable = false, length = 1000)
    private String password;

    @Column(nullable = false, length = 10)
    private String role; //ROLE_USER, ROLE_ADMIN

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

}