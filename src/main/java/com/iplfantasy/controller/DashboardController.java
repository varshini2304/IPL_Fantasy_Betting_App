package com.iplfantasy.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.iplfantasy.entity.User;
import com.iplfantasy.service.LeaderboardService;
import com.iplfantasy.service.MatchService;
import com.iplfantasy.service.TeamService;

@Controller
public class DashboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
            dispatcher.forward(request, response);
            return null;
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("topThree", leaderboardService.getTopThree());
        model.addAttribute("userStanding",
                leaderboardService.getUserStanding(user.getUserId()));
        model.addAttribute("currentMatches", matchService.getCurrentMatches());

        return "dashboard";
    }

    @GetMapping("/teams-points")
    public String teamsPointsTable(HttpSession session, Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
            dispatcher.forward(request, response);
            return null;
        }

        model.addAttribute("teamStatistics", teamService.getTeamStatistics());
        model.addAttribute("username", user.getUsername());

        return "teams_points";
    }
}
