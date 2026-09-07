package com.group16.stardewvalley.model.weather;
import com.group16.stardewvalley.model.time.*;

public enum WeatherCondition {
    SUNNY(1, "Sunny", new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}),
    RAINY(2, "Rainy", new Season[]{Season.Spring, Season.Summer, Season.Fall}),
    STORM(3, "Storm", new Season[]{Season.Spring, Season.Summer, Season.Fall}),
    SNOWY(4, "Snowy", new Season[]{Season.Winter});



    private final int weatherNumber = (int)(Math.random() * 4);;
    private final String name;
    private final Season[] possibleSeasons;

    WeatherCondition(int weatherNumber, String displayName, Season[] possibleSeasons) {
//        this.weatherNumber = weatherNumber;
        this.name = displayName;
        this.possibleSeasons = possibleSeasons;

    }

    public int getWeatherNumber() {
        return weatherNumber;
    }

    public String getWeatherName() {
        return name;
    }

    public static WeatherCondition getByNumber(int number) {
        for (WeatherCondition weather : WeatherCondition.values()) {
            if (weather.getWeatherNumber() == number) {
                return weather;
            }
        }
        return null;
    }

    public static WeatherCondition getByName(String name) {
        for (WeatherCondition weather : WeatherCondition.values()) {
            if (weather.getWeatherName().equalsIgnoreCase(name)) {
                return weather;
            }
        }
        return null;
    }


}
