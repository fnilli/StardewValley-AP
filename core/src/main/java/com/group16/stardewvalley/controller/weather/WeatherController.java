package com.group16.stardewvalley.controller.weather;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.weather.Greenhouse;
import com.group16.stardewvalley.model.weather.WeatherCondition;
import com.group16.stardewvalley.model.app.Game;

import java.security.SecureRandom;
import java.util.regex.Matcher;

public class WeatherController {

    public Result showWeather() {
        return new Result(true, App.getActiveGame().getWeatherCondition().name());
    }

    public Result weatherForecast() {
        SecureRandom secureRandom = new SecureRandom();
        int randomValue = secureRandom.nextInt(4) + 1;

        WeatherCondition weatherCondition = WeatherCondition.getByNumber(randomValue);
        if (weatherCondition == null) {
            return new Result(false, "Invalid weather condition");
        }
        App.getActiveGame().setTomorrowWeatherCondition(weatherCondition);
        return new Result(true, "Tomorrow's weather : " + weatherCondition.getWeatherName());
    }

    public Result buildGreenhouse() {
        if (App.getActiveGame().getCurrentPlayer().getInventory().countWood() < 500) {
            return new Result(false, "You are out of wood");
        }
        if (App.getActiveGame().getCurrentPlayer().getCoin() < 1000) {
            return new Result(false, "You are out of coin");
        }
        App.getActiveGame().getCurrentPlayer().decreaseCoin(1000);
        App.getActiveGame().getCurrentPlayer().getInventory().getItems().put(App.getActiveGame().getCurrentPlayer().getInventory().findWood("wood"), -500);
        Greenhouse greenhouse = new Greenhouse(App.getActiveGame().getCurrentPlayer().getFarm());
        App.getActiveGame().getCurrentPlayer().getFarm().setGreenhouse(greenhouse);
        return new Result(true, "Greenhouse is built!\n500 woods and 1000 coins are used!");
    }

    public Result applyFirelight(int x, int y) {
        if (x < 0 || y < 0 || x >= App.getActiveGame().getMapWidth() || y >= App.getActiveGame().getMapHeight()) {
            return new Result(false, "Invalid coordinates");
        }
        else {
            Tile tile = App.getActiveGame().getMap()[y][x];
            tile.setBurned(true);
            if (tile.getCrop() != null) {
                tile.setCrop(null);
            }
            if (tile.getTree() != null) {
                tile.getTree().setBurned(true);
            }
            if (tile.getItem() != null) {
                tile.setItem(null);
            }
        }
        return new Result(true, "Firelight applied");
    }

    public Result changeWeather(Matcher matcher) {
        String weatherName = matcher.group("Type");
        WeatherCondition targetWeather = WeatherCondition.getByName(weatherName);
        App.getActiveGame().setWeatherCondition(targetWeather);
        return new Result(false, "Weather changed successfully  ^.^");
    }


    }