package com.iplfantasy.csv;

import com.iplfantasy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
@RequiredArgsConstructor
public class UserCsvImporter {

    private final UserService userService;

    public void importUsers(String csvPath) {
        System.out.println("📥 Importing Users from CSV: " + csvPath);

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {

            String line;
            int count = 0;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;
                if (line.toLowerCase().contains("username")) continue; // skip header

                String[] parts = line.split(",");

                if (parts.length < 4) {
                    System.err.println("⚠ Invalid row (ignored): " + line);
                    continue;
                }

                String username = parts[0].trim();
                String email = parts[1].trim();
                String password = parts[2].trim();
                String userType = parts[3].trim();

                try {
                    userService.createUser(username, email, password, userType);
                    System.out.println("✔ Created: " + username);
                    count++;
                } catch (Exception e) {
                    System.err.println("⚠ Failed to import user: " + line + " → " + e.getMessage());
                }
            }

            System.out.println("✅ User CSV Import Completed — " + count + " users processed.");

        } catch (Exception e) {
            System.err.println("❌ CSV Import Error: " + e.getMessage());
        }
    }
}
