package com.example.Board.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.example.Board.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//시큐리티가 로그인 주소 요청을 낚아채서 로긍니을 진행시킨다.
//로그인 진행이 완료되면 시큐리티 session을 만들어준다(Security ContextHolder)
//오브젝트 => Authentication 타입 객체
//Authentication 안에 User정보가 있어야 함
//User 오브젝트 타입 => UserDetails 타입 객체
//Security Session => Authentication => User Details
// Authentication 객체에 저장할 수 있는 유일한 타입
//
public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private User user;
    private Map<String, Object> attributes;

    // 일반 시큐리티 로그인시 사용
    public PrincipalDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //해당 유저의 권한을 리턴함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
        collet.add(new GrantedAuthority() {
                       @Override
                       public String getAuthority() {
                           return user.getRole();
                       }
                   }
        );
        return collet;
    }

    // 리소스 서버로 부터 받는 회원정보
    // @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // User의 PrimaryKey
    // @Override
    public String getName() {
        return user.getId() + "";
    }

}