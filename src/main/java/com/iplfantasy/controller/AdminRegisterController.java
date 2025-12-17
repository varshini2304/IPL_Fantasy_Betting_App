package com.iplfantasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.iplfantasy.entity.User;
import com.iplfantasy.entity.UserType;
import com.iplfantasy.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminRegisterController {

    @Autowired
    private AuthService authService;

    @GetMapping("/register")
    public String showAdminRegister(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null || user.getUserType() != UserType.ADMIN) {
            return "redirect:/admin/login";
        }

        return "admin_register"; 
    }
    
    @PostMapping("/register")
    public String createAdmin(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null || user.getUserType() != UserType.ADMIN) {
            return "redirect:/admin/login";
        }

        boolean created = authService.createAdmin(username, email, password);

        if (!created) {
            model.addAttribute("error", "Admin already exists or username/email already taken!");
            return "admin_register";
        }

        model.addAttribute("msg", "Admin created successfully!");
        return "admin_register";
    }

}
