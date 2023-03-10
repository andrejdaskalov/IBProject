package com.adaskalov.ibproject.web;

import com.adaskalov.ibproject.model.User;
import com.adaskalov.ibproject.model.UserType;
import com.adaskalov.ibproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AccountController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model){
        List<String> roles = Arrays.stream(UserType.values()).map(UserType::name).toList();
        model.addAttribute("roles",roles);
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String name,
                           @RequestParam UserType role){
        User u = new User();
        u.setEmbg(Long.parseLong(username));
        u.setName(name);
        u.setPassword(" ");
        u.setType(role);
        userService.saveUser(u);
        return "redirect:/";
    }


}
