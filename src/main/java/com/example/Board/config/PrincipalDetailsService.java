package com.example.Board.config;

import com.example.Board.entity.User;
import com.example.Board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException { // UserDetails readOnly 파일이아 함수명 userName으로 못바꿈
        User userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntity);
        }

    }

}