package com.iplfantasy.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.iplfantasy.entity.User;
import com.iplfantasy.entity.UserType;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String adminHome(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login");
            dispatcher.forward(request, response);
            return null;
        }
        if (user.getUserType() != UserType.ADMIN) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard");
            dispatcher.forward(request, response);
            return null;
        }

        model.addAttribute("username", user.getUsername());
        return "admin";
    }

    @GetMapping("/csv")
    public String csvPage(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

        User user = (User) session.getAttribute("user");
        if (user == null || user.getUserType() != UserType.ADMIN) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login");
            dispatcher.forward(request, response);
            return null;
        }

    
        return "csv_import";
            
    }
}
