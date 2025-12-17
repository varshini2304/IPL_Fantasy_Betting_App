package com.iplfantasy.csv;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.iplfantasy.service.MatchResultService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MatchResultCsvImporter {

    private final MatchResultService service;

    public int importResults(MultipartFile file) throws Exception {

        int count = 0;

        BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), "UTF-8"));

        String line;
        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) continue;
            if (line.toLowerCase().contains("match")) continue;

            String[] arr = line.split(",");

            Long matchId = Long.parseLong(arr[0].trim());
            String winnerName = arr[1].trim();

            service.updateWinner(matchId, winnerName);
            count++;
        }

        return count;
    }
}
