package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.utils.UserValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminController {
    private final UserService userServiceImplementation;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userServiceImplementation, RoleService roleService, UserValidator userValidator) {
        this.userServiceImplementation = userServiceImplementation;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> showAll(@ModelAttribute("user") User user, @AuthenticationPrincipal User authenticatedUser) {
        List<User> users = userServiceImplementation.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @PostMapping("/admin")
    public ResponseEntity<List<String>> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errorsList = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
        }
        userServiceImplementation.save(user);
        return ResponseEntity.ok(List.of("User " + user.getUsername() + " was created"));
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userServiceImplementation.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }


    @PutMapping("admin/{id}")
    public ResponseEntity<List<String>> update(@PathVariable("id") int id, @Valid @RequestBody User user, BindingResult bindingResult) {
        user.setId(id);
        userValidator.validateToUpdate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errorsList = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
        }
        userServiceImplementation.update(id, user);
        return ResponseEntity.ok(List.of("User " + user.getUsername() + " was updated"));
    }

    @DeleteMapping("admin/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        userServiceImplementation.delete(id);
        return ResponseEntity.ok("User " + id + " was deleted");
    }
}