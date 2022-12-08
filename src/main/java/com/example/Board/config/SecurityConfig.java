package com.example.Board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션, 활성화, 스프링 시큐리티 필터가 스프링 필터 안에 등록된다.
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //비활성화
        http.authorizeRequests()
                .antMatchers("/boards/**").authenticated() // TODO todo사용하기
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")//이 주소로 들어오면 인증&&ADMIN 권한이 필요해요
                .anyRequest().permitAll() //다른 요청은 권한이 필요 없다
                .and()
                .formLogin()//로그인 안되어 있으면 로그인 페이지로 이동하라
                .loginPage("/account/login")
                .usernameParameter("userId")
                .loginProcessingUrl("/account/login")
                .defaultSuccessUrl("/boards");

    }
}




