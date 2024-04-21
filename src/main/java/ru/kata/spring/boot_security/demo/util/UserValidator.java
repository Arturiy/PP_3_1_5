package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userServiceImplementation;

    @Autowired
    public UserValidator(UserService userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (userServiceImplementation.findByUserName(user.getUsername()) != null) {
            errors.rejectValue("userName", "", "Пользователь с таким именем уже существует");
        }
    }

    public void validateToUpdate(Object o, Errors errors) {
        User user = (User) o;
        User foundUsers = userServiceImplementation.findByUserName(user.getUsername()); // foundUsers =
        if (foundUsers != null && foundUsers.getId() != (user.getId())) {
            errors.rejectValue("userName", "", "Пользователь с таким именем уже существует");
        }
    }
}
