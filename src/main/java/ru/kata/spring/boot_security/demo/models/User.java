package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    @NotEmpty(message = "Имя пользователя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя пользователя должено быть от 2 до 100 символов длиной")
    private String userName;

    @Column(name = "email")
    @Email(message = "Некорректный адрес электронной почты")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 2, max = 100, message = "Пароль должен быть от 5 до 100 символов длиной")
    private String password;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Digits(integer = 4, fraction = 0, message = "Некорректный год рождения")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Size.List(@Size(min = 1, message = "У пользователя должна быть хотя бы одна роль"))
    private Set<Role> roles;

    public User() {
    }

    public User(String userName, String password, int yearOfBirth, Set<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.yearOfBirth = yearOfBirth;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return userName;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRolesToString() {
        return roles.stream().map(Role::getName).reduce((a, b) -> a + ", " + b).orElse("");
    }
}
