package com.group16.stardewvalley.model.agriculture;

public enum FertilizerType {
    NONE(false, 0, "none"), // بدون کود
    SPEED_GRO(true, -1, "speed gro"), // یک روز رشد کمتر
    DELUXE_RETAINING_SOIL(false, 0, "deluxe retaining soil"); // آبیاری لازم نیست

    private final boolean affectsGrowth;
    private final int growthModifierInDays; // عدد منفی یعنی رشد سریع‌تر
    private final String name;

    FertilizerType(boolean affectsGrowth, int growthModifierInDays, String name) {
        this.affectsGrowth = affectsGrowth;
        this.name = name;
        this.growthModifierInDays = growthModifierInDays;
    }

    public boolean isAffectsGrowth() {
        return affectsGrowth;
    }

    public String getName() {
        return name;
    }

    public boolean affectsGrowth() {
        return affectsGrowth;
    }

    public int getGrowthModifierInDays() {
        return growthModifierInDays;
    }

    public boolean skipsWatering() {
        return this == DELUXE_RETAINING_SOIL;
    }
}

