package com.iplfantasy.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.iplfantasy.entity.User;
import com.iplfantasy.entity.UserType;
import com.iplfantasy.service.TeamService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CSVImportController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/admin/csv/teams")
    public String importTeams(
            @RequestParam("file") MultipartFile file,
            HttpSession session,
            RedirectAttributes ra) {

        User user = (User) session.getAttribute("user");
        if (user == null || user.getUserType() != UserType.ADMIN)
            return "redirect:/admin/login";

        if (file.isEmpty()) {
            ra.addFlashAttribute("error", "Please select a CSV file");
            return "redirect:/admin/csv";
        }

        int count = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                if (header) { header = false; continue; }
                String[] arr = line.split(",");
                teamService.createOrUpdate(arr[0].trim(), Integer.parseInt(arr[1].trim()));
                count++;
            }

        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/csv";
        }

        ra.addFlashAttribute("msg", count + " teams imported successfully");
        return "redirect:/admin/csv";
    }
}
