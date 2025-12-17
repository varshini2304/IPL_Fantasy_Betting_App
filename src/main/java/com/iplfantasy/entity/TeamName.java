package com.iplfantasy.entity;

public enum TeamName {
    ROYAL_CHALLENGERS_BENGALURU("Royal Challengers Bengaluru"),
    CHENNAI_SUPER_KINGS("Chennai Super Kings"),
    MUMBAI_INDIANS("Mumbai Indians"),
    KOLKATA_KNIGHT_RIDERS("Kolkata Knight Riders"),
    SUNRISERS_HYDERABAD("Sunrisers Hyderabad"),
    RAJASTHAN_ROYALS("Rajasthan Royals"),
    PUNJAB_KINGS("Punjab Kings"),
    DELHI_CAPITALS("Delhi Capitals");

    private final String displayName;

    TeamName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static TeamName fromString(String name) {
        if (name == null) {
            return null;
        }
        String normalized = name.trim();
        for (TeamName teamName : TeamName.values()) {
            if (teamName.displayName.equalsIgnoreCase(normalized) ||
                    teamName.name().equalsIgnoreCase(normalized.replace(" ", "_"))) {
                return teamName;
            }
        }
        return null;
    }
}
