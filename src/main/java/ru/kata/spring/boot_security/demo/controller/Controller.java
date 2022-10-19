package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@org.springframework.stereotype.Controller
public class Controller {

    private final UserService userService;
    private PasswordEncoder passwordEncoder;
    User user;

    @Autowired
    public Controller(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", userService.listAll());
        return "index";
    }

    @GetMapping("/users")
    public String index1(Model model) {
        model.addAttribute("users", userService.listAll());
        return "index";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping(value = "/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.show(id));
        return "update";
    }

    @PostMapping(value = "/users/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")

    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteIfExists(@PathVariable User user) {
        userService.delete(user.getId());
        return "redirect:/users";
    }

    @GetMapping("/user")
    public String getUser(Principal principal, ModelMap model) {
        user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}
