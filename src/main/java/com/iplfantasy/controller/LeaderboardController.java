package com.iplfantasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.iplfantasy.entity.User;
import com.iplfantasy.service.LeaderboardService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @GetMapping("/leaderboard")
    public String leaderboard(Model model, HttpSession session) {

        User u = (User) session.getAttribute("user");

        model.addAttribute("leaderboard", leaderboardService.getLeaderboard());
        model.addAttribute("topThree", leaderboardService.getTopThree());

        if (u != null) {
            model.addAttribute("userStanding",
                    leaderboardService.getUserStanding(u.getUserId()));
        }

        return "leaderboard";
    }
}
