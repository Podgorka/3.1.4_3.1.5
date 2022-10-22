package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Component
public class UserServiceImpl implements UserDetailsService, UserService {

    @PersistenceContext
    private EntityManager entityManager;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);


    @Autowired
    public UserServiceImpl(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }
    public UserServiceImpl() {}


    @Override
    public List<User> listAll() {
        String jpql = "SELECT c FROM User c";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }
    @Transactional
    @Override
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);

    }
    @Transactional
    @Override
    public void update(int id, User updatedUser) {
        User userToBeUpdated = show(id);
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setLast_name(updatedUser.getLast_name());
        userToBeUpdated.setEmail(updatedUser.getEmail());
        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userToBeUpdated.setRoles(updatedUser.getRoles());
    }

    @Override
    public User show(int id) {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        Optional<User> userOpt = Optional.ofNullable(user);
        User personNewThrow;
        try {
            personNewThrow = userOpt.orElseThrow(Exception::new);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
        return personNewThrow;
    }

}