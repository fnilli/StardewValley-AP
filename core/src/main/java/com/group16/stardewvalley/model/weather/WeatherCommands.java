package com.group16.stardewvalley.model.weather;
import com.group16.stardewvalley.model.menu.CommandsInterface;

import java.util.regex.*;

public enum WeatherCommands implements CommandsInterface {
    SHOW_WEATHER("^\\s*weather\\s*$"),
    FORECAST("^\\s*weather\\s*forecast\\s*$") ,
    CHEAT_WEATHER_SET("^\\s*cheat\\s*weather\\s*set\\s*(?<Type>\\S+)\\s*$"),
    BUILD_GREENHOUSE("^\\s*greenhouse\\s*build\\s*$");


    private final String pattern;

    WeatherCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
