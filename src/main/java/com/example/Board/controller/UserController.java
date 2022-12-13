package com.example.Board.controller;

import com.example.Board.entity.User;
import com.example.Board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {

        return "layout/login";
    }

    @PostMapping("/login")
    public String login() {

        return "layout/board/mainPage";
    }

    @GetMapping("/join")
    public String join() {

        return "layout/join";
    }

    @PostMapping("/join")
    public String join(User user) {
        userService.saveUser(user);

        return "redirect:/account/login";
    }

}