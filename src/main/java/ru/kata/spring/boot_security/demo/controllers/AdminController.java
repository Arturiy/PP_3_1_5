package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

@Controller
public class AdminController {
    private final UserService userServiceImplementation;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userServiceImplementation, RoleService roleService) {
        this.userServiceImplementation = userServiceImplementation;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAll(@ModelAttribute("user") User user, @AuthenticationPrincipal User authenticatedUser, Model model) {
        model.addAttribute("authenticatedUser", authenticatedUser);
        model.addAttribute("users", userServiceImplementation.findAll());
        model.addAttribute("allRoles", roleService.findAll());
        return "adminPage";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("emptyUser") @Valid User user) {
        userServiceImplementation.save(user);
        return "redirect:/admin";
    }

    @PostMapping("admin/{id}")
    public String update(@ModelAttribute("user") User user) {
        userServiceImplementation.update(user.getId(), user);
        return "redirect:/admin";
    }

    @PostMapping("admin/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userServiceImplementation.delete(id);
        return "redirect:/admin";
    }
}