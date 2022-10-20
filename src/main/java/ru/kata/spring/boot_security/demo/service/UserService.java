package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService extends UserDetailsService {

     List<User> listAll();

    @Transactional
     void delete(int id);

    @Transactional
     void save(User user);

    @Transactional
     void update(int id, User updatedUser);


     User show(int id);

}
