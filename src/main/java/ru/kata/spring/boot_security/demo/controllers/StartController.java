package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    @GetMapping("/admin")
    public String showIndexPageAdmin() {
        return "adminPage";
    }

    @GetMapping("/user")
    public String showIndexPageUser() {
        return "userPage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
