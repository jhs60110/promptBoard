package com.example.Board.controller;

import com.example.Board.entity.User;
import com.example.Board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
@RequestMapping("/account")
public class UserController {


    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {

        return "layout/authority/login";
    }

    @PostMapping("/login")
    public String login() {

        return "layout/board/mainPage";
    }

    @GetMapping("/join")
    public String join() {

        return "layout/authority/join";
    }

    @PostMapping("/join")
    public String join(User user) {
        userService.setUser(user);

        return "redirect:/account/login";
    }

}