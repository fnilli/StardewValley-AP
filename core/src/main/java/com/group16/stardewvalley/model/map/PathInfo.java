package com.group16.stardewvalley.model.map;

import java.util.List;

public class PathInfo {
    private final boolean valid;
    private final List<Pos> path;
    private final int energyCost;
    private final String message;

    private PathInfo(boolean valid, List<Pos> path, int energyCost, String message) {
        this.valid = valid;
        this.path = path;
        this.energyCost = energyCost;
        this.message = message;
    }

    public static PathInfo valid(List<Pos> path, int energyCost) {
        return new PathInfo(true, path, energyCost, null);
    }

    public static PathInfo invalid(String message) {
        return new PathInfo(false, null, 0, message);
    }

    public boolean isValid() {
        return valid;
    }

    public int energyCost() {
        return energyCost;
    }

    public String message() {
        return message;
    }
}

