package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.List;

/*
Добавил реквест маппинг "api/admin".
Сделал внедрение бинов через интерфейсы
Убрал не нужный Principal из showAll от предыдущей задачи
*/

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final Validator validator;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, @Qualifier("userValidator") Validator validator, RoleService roleService) {
        this.userService = userService;
        this.validator = validator;
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> showAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping()
    public ResponseEntity<List<String>> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errorsList = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
        }
        userService.save(user);
        return ResponseEntity.ok(List.of("User " + user.getUsername() + " was created"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }


    @PutMapping("/{id}")
    public ResponseEntity<List<String>> update(@PathVariable("id") long id, @Valid @RequestBody User user, BindingResult bindingResult) {
        user.setId(id);
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errorsList = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
        }
        userService.update(id, user);
        return ResponseEntity.ok(List.of("User " + user.getUsername() + " was updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        userService.delete(id);
        return ResponseEntity.ok("User " + id + " was deleted");
    }
}