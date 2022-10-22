package ru.kata.spring.boot_security.demo.service;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class TestClass {

    final UserService userService;

    public TestClass(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        User user = new User("user", "user","user", "user", "user", roles);
        userService.save(user);
        roles.add(roleAdmin);
        User admin = new User("admin", "admin","admin", "admin", "admin", roles);
        userService.save(admin);
    }

}
