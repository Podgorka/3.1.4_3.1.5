package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RegistrationService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.sql.Struct;

@Controller
public class AdminController {

    private final UserService userService;
    private RegistrationService registrationService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", userService.listAll());
        return "index";
    }    @GetMapping("/users")
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
    public String createUser(@ModelAttribute ("user") User user){
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


    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute ("user") User user) {
        return "/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user) {
        registrationService.register(user);
        return "redirect:/";
    }
}
