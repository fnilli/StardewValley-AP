package com.group16.stardewvalley.controller.trade;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.user.Player;

import java.util.regex.Matcher;

public class TradeController {


    public Result startTrade() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Player player : App.getActiveGame().getPlayers()) {
            sb.append(i);
            sb.append("-");
            sb.append(player.getName());
            sb.append("\n");
        }

        return new Result(true, sb.toString());
    }

    public Result trade(Matcher matcher, String input) {
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        String username = matcher.group("username");
        String type = matcher.group("type");
        String itemName = matcher.group("item");
        String amountStr = matcher.group("amount");
        String priceStr = null;
        String targetItemName = null;
        String targetAmountStr = null;
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            return new Result(false, "amount is invalid");
        }

        if (type.equalsIgnoreCase("offer")) {
            priceStr = matcher.group("price");
            int price;
            targetItemName = matcher.group("targetItemName");
            targetAmountStr = matcher.group("targetAmount");
            int targetAmount;
            try {
                price = Integer.parseInt(priceStr);
                targetAmount = Integer.parseInt(priceStr);
            } catch (NumberFormatException e) {
                return new Result(false, "price or targetAmount is invalid ");
            }
        }

        // بازیکن مورد نظر وجود نداشت
        if (App.getActiveGame().getPlayerByUsername(username) == null) {
            return new Result(false, "Player not found!");
        }

        // اصلا این کالا را نداشته باشد
        if (currentPlayer.getInventory().getItemByName(itemName) == null) {
            return new Result(false, "You don't have it in your inventory");
        }
        Item itemForTrade = currentPlayer.getInventory().getItemByName(itemName);

        // تعداد کافی از این کالا نداشته باشد
        if (currentPlayer.getInventory().getNumberOfItem(itemForTrade) < amount) {
            return new Result(false, "You don't have enough number of this item");
        }

        // هر دو روش را انتخاب کرده است
        if (type.equalsIgnoreCase("request") && (priceStr != null || targetItemName != null || targetAmountStr != null)) {
            return new Result(false, "Request type cannot have offer parameters!");
        }

        Player targetPlayer = App.getActiveGame().getPlayerByUsername(username);

        // هیچ خطایی نیست
        return new Result(true, "Your " + type + " sent to " + username + " successfully");
    }


}