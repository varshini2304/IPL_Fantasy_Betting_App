package com.iplfantasy.controller;

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
    public String adminHome(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        if (user == null) return "redirect:/admin/login";
        if (user.getUserType() != UserType.ADMIN) return "redirect:/dashboard";

        model.addAttribute("username", user.getUsername());
        return "admin";
    }

    @GetMapping("/csv")
    public String csvPage(HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null || user.getUserType() != UserType.ADMIN)
            return "redirect:/admin/login";

        return "csv_import";
    }
}
