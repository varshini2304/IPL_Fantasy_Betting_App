package com.iplfantasy.csv;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.iplfantasy.service.TeamService;

@Component
public class TeamCsvImporter {

    @Autowired
    private TeamService teamService;

    public int importTeams(MultipartFile file) throws Exception {

        int count = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), "UTF-8"))) {

            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;
                if (line.startsWith("team")) continue;

                String[] arr = line.split(",");
                String name = arr[0].trim();
                int points = Integer.parseInt(arr[1].trim());

                teamService.createOrUpdate(name, points);
                count++;
            }
        }
        return count;
    }
}
