package demo.service;

import demo.model.Role;
import demo.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);
        User user = new User( "user","user", "user", "user", roles);
        userService.saveUser(user);
        roles.add(roleAdmin);
        User admin = new User( "admin","admin", "admin", "admin", roles);
        userService.saveUser(admin);
    }

}