package com.iplfantasy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.iplfantasy.service.AuthService;

@Component
public class DefaultAdminInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static boolean alreadyRun = false;

    @Autowired
    private AuthService authService;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        if (alreadyRun) return;
        alreadyRun = true;

        try {
            authService.createDefaultAdminIfNotExists();
        } catch (Exception e) {
            System.err.println("Error creating default admin: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

