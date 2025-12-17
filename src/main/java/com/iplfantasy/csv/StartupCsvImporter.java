package com.iplfantasy.csv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.iplfantasy.entity.Team;
import com.iplfantasy.service.PlayerService;
import com.iplfantasy.service.TeamService;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class StartupCsvImporter implements ApplicationListener<ContextRefreshedEvent> {

    private static boolean alreadyRun = false;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadyRun)
            return;
        alreadyRun = true;

        try (InputStream is = getClass().getResourceAsStream("/teams.csv")) {
            if (is == null) {
                System.out.println("StartupCsvImporter: teams.csv not found on classpath.");
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            int count = 0;
            int playerCount = 0;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                if (line.toLowerCase().startsWith("team"))
                    continue;

               
                int firstComma = line.indexOf(',');
                int secondComma = line.indexOf(',', firstComma + 1);

                if (firstComma == -1) {
                    System.err.println("Skipping invalid line (no comma): " + line);
                    continue;
                }

                String name = line.substring(0, firstComma).trim();
                String pointsStr = (secondComma != -1)
                        ? line.substring(firstComma + 1, secondComma).trim()
                        : line.substring(firstComma + 1).trim();
                String playersStr = (secondComma != -1)
                        ? line.substring(secondComma + 1).trim()
                        : "";

                int points = 0;
                try {
                    points = Integer.parseInt(pointsStr);
                } catch (NumberFormatException e) {
                }

                Team team = teamService.createOrUpdate(name, points);
                System.out.println(
                        "Processing team: " + name + " (ID: " + team.getTeamId() + ") with " + points + " points");

                if (!playersStr.isEmpty()) {
                    String[] players = playersStr.split("\\|");
                    System.out.println("  Found " + players.length + " players to import");
                    for (String playerName : players) {
                        String trimmedName = playerName.trim();
                        if (!trimmedName.isEmpty()) {
                            try {
                                playerService.createOrUpdate(trimmedName, team);
                                playerCount++;
                                System.out.println("  ✓ Added player: " + trimmedName);
                            } catch (Exception e) {
                                System.err.println("  ✗ Error adding player '" + trimmedName + "': " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    System.out.println("  No players found for team: " + name);
                }

                count++;
            }
            System.out.println(
                    "StartupCsvImporter: imported " + count + " teams and " + playerCount + " players from teams.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}