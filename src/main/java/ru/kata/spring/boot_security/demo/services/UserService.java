package ru.kata.spring.boot_security.demo.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);

    List<User> findAll();

    User findById(int id);

    void save(User user);

    void update(int id, User updatedUser);

    void delete(int id);
}
