package com.iplfantasy.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public String loginPage(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String targetPath = user.getUserType() == UserType.ADMIN ? "/admin" : "/dashboard";
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetPath);
            dispatcher.forward(request, response);
            return null;
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        User user = userService.loginByEmail(email, password);

        if (user == null) {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }

        session.setAttribute("user", user);

        // Use redirect for POST requests to avoid method mismatch
        return user.getUserType() == UserType.ADMIN
                ? "redirect:/admin"
                : "redirect:/dashboard";
    }

    @GetMapping("/admin/login")
    public String adminLoginPage(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String targetPath = user.getUserType() == UserType.ADMIN ? "/admin" : "/dashboard";
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetPath);
            dispatcher.forward(request, response);
            return null;
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
        // Use redirect for POST requests
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

        // Use redirect for POST requests
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        session.invalidate();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
        return null;
    }
}
