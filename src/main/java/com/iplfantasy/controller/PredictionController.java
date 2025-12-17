package com.iplfantasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.Prediction;
import com.iplfantasy.entity.Team;
import com.iplfantasy.entity.User;
import com.iplfantasy.service.MatchService;
import com.iplfantasy.service.PlayerService;
import com.iplfantasy.service.PredictionService;
import com.iplfantasy.service.TeamService;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

@Controller
public class PredictionController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private PlayerService playerService;

    @GetMapping("/predict/{matchId}")
    public String predictionPage(
            @PathVariable Long matchId,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null)
            return "login";

        Match match = matchService.get(matchId);

        Prediction existing = predictionService.getUserPredictions(user)
                .stream()
                .filter(p -> p.getMatch().getMatchId().equals(matchId))
                .findFirst()
                .orElse(null);

        Team team1 = teamService.getById(match.getTeam1().getTeamId());
        Team team2 = teamService.getById(match.getTeam2().getTeamId());

        List<com.iplfantasy.entity.Player> team1Players = playerService.getPlayersByTeam(team1);
        List<com.iplfantasy.entity.Player> team2Players = playerService.getPlayersByTeam(team2);

        List<com.iplfantasy.entity.Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(team1Players);
        allPlayers.addAll(team2Players);

        System.out.println("Team1: " + team1.getTeamName() + " - Players: " + team1Players.size());
        System.out.println("Team2: " + team2.getTeamName() + " - Players: " + team2Players.size());

        model.addAttribute("match", match);
        model.addAttribute("existing", existing);
        model.addAttribute("players", allPlayers);
        model.addAttribute("team1Players", team1Players);
        model.addAttribute("team2Players", team2Players);

        return "predict";
    }

    @PostMapping("/predict/save")
    public String savePrediction(
            @RequestParam Long matchId,
            @RequestParam Long teamId,
            @RequestParam(required = false) Long tossWinnerId,
            @RequestParam(required = false) String topScorer,
            @RequestParam(required = false) String manOfTheMatch,
            @RequestParam(required = false) Integer totalRunsMin,
            @RequestParam(required = false) Integer totalRunsMax,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null)
            return "login";

        Match match = matchService.get(matchId);
        Team predicted = teamService.getById(teamId);

        try {
            Prediction prediction = predictionService.createOrUpdate(user, match, predicted);

            if (tossWinnerId != null) {
                Team tossWinner = teamService.getById(tossWinnerId);
                predictionService.updateTossWinner(prediction, tossWinner);
            }

            if (topScorer != null && !topScorer.trim().isEmpty()) {
                predictionService.updateTopScorer(prediction, topScorer.trim());
            }

            if (manOfTheMatch != null && !manOfTheMatch.trim().isEmpty()) {
                predictionService.updateManOfTheMatch(prediction, manOfTheMatch.trim());
            }

            if (totalRunsMin != null && totalRunsMax != null) {
                predictionService.updateTotalRuns(prediction, totalRunsMin, totalRunsMax);
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("match", match);
            Prediction existing = predictionService.getUserPredictions(user)
                    .stream()
                    .filter(p -> p.getMatch().getMatchId().equals(matchId))
                    .findFirst()
                    .orElse(null);
            model.addAttribute("existing", existing);
            return "predict";
        }

        return "redirect:/my-predictions";
    }

    @GetMapping("/my-predictions")
    public String myPredictions(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null)
            return "login";

        model.addAttribute(
                "predictions",
                predictionService.getUserPredictions(user));

        return "my_predictions";
    }
}
