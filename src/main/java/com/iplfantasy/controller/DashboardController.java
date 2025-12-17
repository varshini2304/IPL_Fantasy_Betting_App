package com.iplfantasy.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.iplfantasy.entity.User;
import com.iplfantasy.service.LeaderboardService;
import com.iplfantasy.service.MatchService;

@Controller
public class DashboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @Autowired
    private MatchService matchService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        model.addAttribute("username", user.getUsername());
        model.addAttribute("topThree", leaderboardService.getTopThree());
        model.addAttribute("userStanding",
                leaderboardService.getUserStanding(user.getUserId()));
        model.addAttribute("currentMatches", matchService.getCurrentMatches());

        return "dashboard";
    }
}
