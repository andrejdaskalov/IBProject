package com.adaskalov.ibproject.web;

import com.adaskalov.ibproject.model.Prescription;
import com.adaskalov.ibproject.model.User;
import com.adaskalov.ibproject.model.UserType;
import com.adaskalov.ibproject.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String Index(Model model) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserById(Long.parseLong(username));
        if (user.getType().equals(UserType.DOCTOR))
            return "redirect:/prescription";
        else if (user.getType().equals(UserType.ADMIN)) {
            return "redirect:/medicine";
        }
        else {
            List<Prescription> prescriptions = user.getPrescriptions();
            model.addAttribute("prescriptions",prescriptions);
            model.addAttribute("title","Your Prescriptions:");
            return "prescriptions";
        }
    }
}
