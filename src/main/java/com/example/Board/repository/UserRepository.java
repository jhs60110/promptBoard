package com.example.Board.repository;

import com.example.Board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
//@Repository 어노테이션 없어도 됨 jpaRepo가 들고있음
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    User findByUserName(String userName);

}

