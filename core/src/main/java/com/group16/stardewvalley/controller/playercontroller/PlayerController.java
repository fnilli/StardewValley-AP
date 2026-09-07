package com.group16.stardewvalley.controller.playercontroller;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Flower;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.Location;
import com.group16.stardewvalley.model.user.Player;

import java.util.regex.Matcher;

public class PlayerController {

    public Result showCoin() {
        Player current = App.getActiveGame().getCurrentPlayer();
        return new Result(true, "coin : " + current.getCoin());
    }

    public Result cheatAddCoin(Matcher matcher) {
        String amountStr = matcher.group("amount");
        int amount = Integer.parseInt(amountStr);
        Player current = App.getActiveGame().getCurrentPlayer();
        current.setCoin(amount);
        return new Result(true, "coin set successfully new coin = " + current.getCoin());
    }

    public Result cheatSetPos(Matcher matcher) {
        String xStr = matcher.group("x");
        String yStr = matcher.group("y");
        int x = Integer.parseInt(xStr);
        int y = Integer.parseInt(yStr);
        Player current = App.getActiveGame().getCurrentPlayer();
        current.setPosition(x, y);
        return new Result(true, "Pos set successfully");
    }

//    public Result cheatSetLocation(Matcher matcher) {
//        String location = matcher.group("location");
//        Location target = Location.getLocationByName(location);
//    }

    public Result cheatSetLocation(Matcher matcher) {
        String locationName = matcher.group("location");
        Location location = Location.getLocationByName(locationName);
        App.getActiveGame().getCurrentPlayer().setLocation(location);
        return new Result(true, "location set successfully");
    }

    public Result showInventory() {
        StringBuilder sb = new StringBuilder();
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        sb.append("Inventory :\n");
        for (Item item : currentPlayer.getInventory().getItems().keySet()){
            sb.append(item.getName());
            sb.append("\n");
        }
        return new Result(true, sb.toString());
    }

//    public Result cheatAddItem(Matcher matcher) {
//        String itemName = matcher.group("itemName");
//        String countStr = matcher.group("count");
//        int count = Integer.parseInt(countStr);
//
//        App.getActiveGame().getCurrentPlayer().getInventory().addItem(App.getActiveGame().
//    }

    public Result setFriendship(Matcher matcher) {
        String username = matcher.group("username");
        String amountStr = matcher.group("amount");
        Player target = App.getActiveGame().getPlayerByUsername(username);
        int amount = Integer.parseInt(amountStr);
        App.getActiveGame().getCurrentPlayer().getOrCreateInteractionWith(target).setFriendshipLevel(amount);
        return new Result(true, "friendship level set successfully");
    }

    public Result cheatAddFlower() {
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        currentPlayer.getInventory().addItem(new Flower("Flower", 50), 1);
        return new Result(true, "added");
    }

}


