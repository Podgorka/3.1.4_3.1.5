package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        roles.add(roleAdmin);
        User user = new User("b", "b","b", "b", "$2a$12$hQGLpuMJbYXhzpKq7y353OA3C7neo3y3le5q2qpNuzOwFdwMIKBCS", roles);
        userService.save(user);
    }

}
