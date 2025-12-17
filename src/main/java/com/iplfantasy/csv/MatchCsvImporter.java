package com.iplfantasy.csv;

import com.iplfantasy.entity.Team;
import com.iplfantasy.service.MatchService;
import com.iplfantasy.service.TeamService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class MatchCsvImporter {

    private final MatchService matchService;
    private final TeamService teamService;

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public int importMatches(MultipartFile file) {

        int count = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), "UTF-8"))) {

            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.toLowerCase().contains("team1")) continue;

                String[] arr = line.split(",");

                if (arr.length < 3) continue;

                String t1Name = arr[0].trim();
                String t2Name = arr[1].trim();
                String start = arr[2].trim();

                Team t1 = teamService.getByName(t1Name);
                Team t2 = teamService.getByName(t2Name);

                if (t1 == null || t2 == null) continue;

                LocalDateTime startTime = LocalDateTime.parse(start, FORMAT);

                matchService.create(t1, t2, startTime);
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}
