package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

/*
Добавил интерфейс RoleService.
* */

public interface RoleService {
    Role getRoleById(Long id);

    List<Role> findAll();

    void save(Role role);

    void delete(Long id);

    void update(Long id, Role updatedRole);
}
