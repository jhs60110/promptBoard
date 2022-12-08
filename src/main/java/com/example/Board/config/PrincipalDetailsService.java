package com.example.Board.config;

import com.example.Board.entity.User;
import com.example.Board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/account/login")
//login 요청이 오면 자동으로 UserDetailsService타입으로
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //시큐리티 session = Authentication(내부 UserDetails)
    @Override                      //여기에 들어갈 파라미터 html name 속성이름이랑 맞춰야함
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException { // UserDetails readOnly 파일이아 함수명 userName으로 못바꿈
        User userEntity = userRepository.findByuserId(userId);
        if (userEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntity);
        }

    }

}