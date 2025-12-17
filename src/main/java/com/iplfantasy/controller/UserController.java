package com.iplfantasy.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.iplfantasy.entity.User;

@Controller
public class UserController {

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
            dispatcher.forward(request, response);
            return null;
        }

        model.addAttribute("user", user);
        return "profile";
    }
}
