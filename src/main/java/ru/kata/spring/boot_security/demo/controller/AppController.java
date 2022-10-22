package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
public class AppController {

    private final UserService userService;

    User user;

    @Autowired
    public AppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal, ModelMap model1, Model model2) {
        model.addAttribute("users", userService.listAll());
        User user1 = (User) userService.loadUserByUsername(principal.getName());
        model1.addAttribute("user1", user1);
        model2.addAttribute("user2", new User());
        return "index";
    }

    @GetMapping("/users")
    public String index1( Model model, Principal principal, ModelMap model1, Model model2) {
        model.addAttribute("users", userService.listAll());
        User user1 = (User) userService.loadUserByUsername(principal.getName());
        model1.addAttribute("user1", user1);
        model2.addAttribute("user2", new User());
        return "index";
    }


    @PostMapping
    public String createUser(@ModelAttribute("user") User user) {

        userService.save(user);

        return "redirect:/";
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
    @GetMapping("/onlyForUsers")
    public String getUserOnlyForUsers(Principal principal, ModelMap model) {
        user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "onlyForUsers";
    }

}
