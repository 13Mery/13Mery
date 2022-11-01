package com.maria.adoption.controller;

import com.maria.adoption.entities.User;
import com.maria.adoption.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @Valid User formUser, BindingResult bindingResult, HttpServletRequest request) throws ServletException {
        User user = userService.findByUsername(formUser.getUsername());
        if (user != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There already exists a user with that username.");
        }
        if (!bindingResult.hasErrors()) {
            String password = formUser.getPassword();
            userService.saveUser(formUser);
            System.out.println(formUser.getPassword());
            request.login(formUser.getUsername(), password);
            return "redirect:/";
        }
        return "register";
    }
}
