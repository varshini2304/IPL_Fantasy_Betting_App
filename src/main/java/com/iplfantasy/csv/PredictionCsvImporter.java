package com.iplfantasy.csv;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.Team;
import com.iplfantasy.entity.User;
import com.iplfantasy.service.MatchService;
import com.iplfantasy.service.PredictionService;
import com.iplfantasy.service.TeamService;
import com.iplfantasy.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PredictionCsvImporter {

    private final UserService userService;
    private final MatchService matchService;
    private final TeamService teamService;
    private final PredictionService predictionService;

    public int importPredictions(MultipartFile file) throws Exception {

        int count = 0;

        BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), "UTF-8"));

        String line;
        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) continue;
            if (line.toLowerCase().contains("username")) continue;

            String[] arr = line.split(",");

            String username = arr[0].trim();
            Long matchId = Long.parseLong(arr[1].trim());
            String teamName = arr[2].trim();

            User user = userService.getByUsername(username);
            Match match = matchService.get(matchId);
            Team team = teamService.getByName(teamName);

            predictionService.createOrUpdate(user, match, team);
            count++;
        }

        return count;
    }
}
