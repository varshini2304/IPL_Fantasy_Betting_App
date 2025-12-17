package com.iplfantasy.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.iplfantasy.entity.User;
import com.iplfantasy.entity.UserType;
import com.iplfantasy.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            return user.getUserType() == UserType.ADMIN
                    ? "redirect:/admin"
                    : "redirect:/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        User user = userService.login(username, password);

        if (user == null) {
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }

        session.setAttribute("user", user);

        return user.getUserType() == UserType.ADMIN
                ? "redirect:/admin"
                : "redirect:/dashboard";
    }

   
    @GetMapping("/admin/login")
    public String adminLoginPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            if (user.getUserType() == UserType.ADMIN) {
                return "redirect:/admin";
            } else {
                return "redirect:/dashboard";
            }
        }
        return "admin_login";
    }

    @PostMapping("/admin/login")
    public String doAdminLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        User user = userService.login(username, password);

        if (user == null) {
            model.addAttribute("error", "Invalid admin credentials!");
            return "admin_login";
        }

        if (user.getUserType() != UserType.ADMIN) {
            model.addAttribute("error", "Access denied. Admin credentials required.");
            return "admin_login";
        }

        session.setAttribute("user", user);
        return "redirect:/admin";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        User u = userService.register(username, email, password);

        if (u == null) {
            model.addAttribute("error", "Username or Email already exists!");
            return "register";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
