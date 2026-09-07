package com.group16.stardewvalley.model.time;

public enum DaysOfWeek {
    Saturday(0, "Sat"),
    Sunday(1, "Sun"),
    Monday(2, "Mon"),
    Tuesday(3, "Tue"),
    Wednesday(4, "Wed"),
    Thursday(5, "Thu"),
    Friday(6, "Fri");

    private int index;
    private String name;

    DaysOfWeek(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
