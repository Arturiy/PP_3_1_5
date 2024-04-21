package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService userServiceImplementation;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userServiceImplementation, RoleService roleService, UserValidator userValidator) {
        this.userServiceImplementation = userServiceImplementation;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("users", userServiceImplementation.findAll());
        return "admin/index";
    }

    @GetMapping("admin/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userServiceImplementation.findById(id));
        return "admin/show";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/new";
    }

    @PostMapping("/admin")
    public String create(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "admin/new";
        }

        userServiceImplementation.save(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userServiceImplementation.findById(id));
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/edit";
    }

    @PostMapping("admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        userValidator.validateToUpdate(user, bindingResult);
        if (bindingResult.hasErrors()) return "admin/edit";
        userServiceImplementation.update(id, user);
        return "redirect:/admin";
    }

    @PostMapping("admin/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userServiceImplementation.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        User user = userServiceImplementation.findByUserName(principal.getName());
        model.addAttribute("user", user);
        return "user/userPage";
    }
}
