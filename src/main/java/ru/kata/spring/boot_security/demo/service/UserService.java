package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Component
public class UserService implements UserDetailsService {

    @PersistenceContext
    private final EntityManager entityManager;
    final UserRepository userRepository;

    @Autowired
    public UserService(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }



    public List<User> listAll() {
        String jpql = "SELECT c FROM User c";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }
    @Transactional
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
    @Transactional
    public void save(User user) {
        entityManager.persist(user);

    }
    @Transactional
    public void update(int id, User updatedUser) {
        User userToBeUpdated = show(id);
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setLast_name(updatedUser.getLast_name());
        userToBeUpdated.setEmail(updatedUser.getEmail());
        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setPassword(updatedUser.getPassword());
        userToBeUpdated.setRoles(updatedUser.getRoles());
    }

    public User show(int id) {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}