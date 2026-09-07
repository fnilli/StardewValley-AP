package com.group16.stardewvalley.model.food;

public enum BuffType {
    NONE("", 0, ""),
    MAX_ENERGY_100("+100 Max Energy", 5, "Buff/Max_Energy_Buff.png"),
    MAX_ENERGY_50("+50 Max Energy", 3, "Buff/Max_Energy_Buff.png"),
    FARMING_5H("Farming", 5, "Buff/Farming_Skill_Icon.png"),
    FORAGING_5H("Foraging", 5, "Buff/Foraging_Skill_Icon.png"),
    FORAGING_11H("Foraging", 11, "Buff/Foraging_Skill_Icon.png"),
    FISHING_5H("Fishing", 5, "Buff/Fishing_Skill_Icon.png"),
    FISHING_10H("Fishing", 10, "Buff/Fishing_Skill_Icon.png"),
    MINING_5H("Mining", 5, "Buff/Mining_Skill_Icon.png");

    private final String description;
    private final int durationHours;
    private final String texturePath;

    BuffType(String description, int durationHours, String texturePath) {
        this.description = description;
        this.durationHours = durationHours;
        this.texturePath = texturePath;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public String getTexturePath() {
        return texturePath;
    }
}
