package com.iplfantasy.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.Team;
import com.iplfantasy.entity.User;
import com.iplfantasy.service.MatchService;
import com.iplfantasy.service.PlayerService;
import com.iplfantasy.service.TeamService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    private boolean isAdmin(User u) {
        return u != null && u.getUserType().name().equals("ADMIN");
    }

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @GetMapping("/admin/add")
    public String addMatchPage(HttpSession session, Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        User u = (User) session.getAttribute("user");
        if (!isAdmin(u)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login");
            dispatcher.forward(request, response);
            return null;
        }

        model.addAttribute("teams", teamService.getAllTeams());
        return "match_add";
    }

    @PostMapping("/admin/add")
    public String addMatch(
            @RequestParam Long team1Id,
            @RequestParam Long team2Id,
            @RequestParam String startTime,
            HttpSession session) {

        User u = (User) session.getAttribute("user");
        if (!isAdmin(u))
            return "redirect:/admin/login";

        Team t1 = teamService.getById(team1Id);
        Team t2 = teamService.getById(team2Id);

        LocalDateTime start = LocalDateTime.parse(startTime, DTF);
        matchService.create(t1, t2, start);

        return "redirect:/match/admin/list";
    }

    @GetMapping("/admin/list")
    public String listAll(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        User u = (User) session.getAttribute("user");
        if (!isAdmin(u)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login");
            dispatcher.forward(request, response);
            return null;
        }

        model.addAttribute("matches", matchService.getAll());
        model.addAttribute("allTeams", teamService.getAllTeams());

        return "match_list";
    }

    @PostMapping("/admin/toss")
    public String setToss(
            @RequestParam Long matchId,
            @RequestParam String tossTime,
            HttpSession session) {

        User u = (User) session.getAttribute("user");
        if (!isAdmin(u))
            return "redirect:/admin/login";

        LocalDateTime toss = LocalDateTime.parse(tossTime, DTF);
        matchService.setToss(matchId, toss);

        return "redirect:/match/admin/list";
    }

    @PostMapping("/admin/toss-winner")
    public String setTossWinner(
            @RequestParam Long matchId,
            @RequestParam Long tossWinnerTeamId,
            HttpSession session) {

        User u = (User) session.getAttribute("user");
        if (!isAdmin(u))
            return "redirect:/admin/login";

        Team tossWinner = teamService.getById(tossWinnerTeamId);
        matchService.setTossWinner(matchId, tossWinner);

        return "redirect:/match/admin/list";
    }

    @PostMapping("/admin/winner")
    public String setWinner(
            @RequestParam Long matchId,
            @RequestParam Long winnerTeamId,
            HttpSession session) {

        User u = (User) session.getAttribute("user");
        if (!isAdmin(u))
            return "redirect:/admin/login";

        Team winner = teamService.getById(winnerTeamId);
        matchService.setWinner(matchId, winner);

        return "redirect:/match/admin/list";
    }

    @PostMapping("/admin/result")
    public String setMatchResult(
            @RequestParam Long matchId,
            @RequestParam(required = false) Long winnerTeamId,
            @RequestParam(required = false) Long tossWinnerTeamId,
            @RequestParam(required = false) String manOfTheMatch,
            @RequestParam(required = false) String topScorer,
            @RequestParam(required = false) Integer winningTeamScore,
            @RequestParam(required = false) Boolean isDraw,
            HttpSession session) {

        User u = (User) session.getAttribute("user");
        if (!isAdmin(u))
            return "redirect:/admin/login";

        Team winner = null;
        if (winnerTeamId != null) {
            winner = teamService.getById(winnerTeamId);
        }

        matchService.setMatchResult(matchId, winner,
                manOfTheMatch != null && !manOfTheMatch.trim().isEmpty() ? manOfTheMatch.trim() : null,
                topScorer != null && !topScorer.trim().isEmpty() ? topScorer.trim() : null,
                winningTeamScore,
                isDraw != null && isDraw);

        return "redirect:/match/admin/list";
    }

    @GetMapping("/upcoming")
    public String upcomingMatches(Model model) {

        model.addAttribute("matches", matchService.getUpcoming());
        return "upcoming_matches";
    }
}
