package com.group16.stardewvalley.controller.relationship;

import com.group16.stardewvalley.model.Message;
import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.items.MarriageRing;
import com.group16.stardewvalley.model.notification;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.PlayerInteraction;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class RelationshipController {


    public Result showFriendship() {
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        List<Player> allPlayers = App.getActiveGame().getPlayers();
        StringBuilder sb = new StringBuilder();

        for (Player player : allPlayers) {
            PlayerInteraction interaction = currentPlayer.getOrCreateInteractionWith(player);

            sb.append("Player name: ");
            sb.append(player.getName());
            sb.append(" | Friendship score: ");
            sb.append(interaction.getFriendshipScore());
            sb.append(" | Friendship level: ");
            sb.append(interaction.getFriendshipLevel());
            sb.append("\n");
        }

        return new Result(true, sb.toString());
    }

    public Result meet(Matcher matcher) {
        String username = matcher.group("username");
        String message = matcher.group("message");
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        Player targetPlayer = App.getActiveGame().getPlayerByUsername(username);
        // این شخص کلا وجود نداشته باشد
        if (targetPlayer == null) {
            return new Result(false, "This person seems to exist only in legends... " +
                    "or maybe your imagination?");
        }

        //در ۸ خانه ی مجاور نباشد
        if (!App.getActiveGame().isAdjacent(currentPlayer.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        // بتواند صحبت کند
        boolean hadTalkedToday = currentPlayer.getOrCreateInteractionWith(targetPlayer).isTalked();
        String newStr = message + currentPlayer.getName();
        Message newMessage = new Message(currentPlayer, newStr);
        targetPlayer.addNotification(newMessage);
        currentPlayer.getOrCreateInteractionWith(targetPlayer).addDialogue(newStr);
        if (!currentPlayer.getOrCreateInteractionWith(targetPlayer).isTalked()) {
            currentPlayer.getOrCreateInteractionWith(targetPlayer).increaseFriendshipLevelScore(20);
            targetPlayer.getOrCreateInteractionWith(currentPlayer).increaseFriendshipLevelScore(20);
        }
        if (currentPlayer.getSpouse() != null) {
            if (currentPlayer.getSpouse().equals(targetPlayer)) {
                currentPlayer.increaseEnergy(50);
                targetPlayer.increaseEnergy(50);
            }
        }

        Message message1 = new Message(currentPlayer, currentPlayer.getName() + " met you , " +
                " you can see talk history with ' talk history' command");
        currentPlayer.addNotification(message1);
        targetPlayer.getOrCreateInteractionWith(currentPlayer).getDialogueHistory().add(message);
        return new Result(true, "Message sent successfully!");
    }

    public Result showTalkHistory(Matcher matcher) {
        String username = matcher.group("username");
        Player targetPlayer = App.getActiveGame().getPlayerByUsername(username);
        Player player = App.getActiveGame().getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        for (String line : player.getOrCreateInteractionWith(targetPlayer).getDialogueHistory()) {
            result.append(line).append("\n");
        }
        return new Result(true, result.toString());

    }

    public Result gift(Matcher matcher) {
        String username = matcher.group("username");
        String itemName = matcher.group("itemName");
        String amountStr = matcher.group("amount");
        int amount = Integer.parseInt(amountStr);
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        Player targetPlayer = App.getActiveGame().getPlayerByUsername(username);


        // سطح یک نباشند
        if (currentPlayer.getOrCreateInteractionWith(targetPlayer).getFriendshipLevel() < 1) {
            return new Result(false, "Friendship level too low! You can't exchange gifts yet.");
        }

        // در نزدیک هم نباشند
        if (!App.getActiveGame().isAdjacent(currentPlayer.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        Item targetItem = currentPlayer.getInventory().getItemByName(itemName);
        if (targetItem == null) {
            return new Result(false, "You don't have enough of this item in your inventory!");
        }

        // به مقدار کافی در اینونتوری اش نداشته باشد
        if (currentPlayer.getInventory().getNumberOfItem(targetItem) < amount) {
            return new Result(false, "You don't have enough of this item in your inventory!");
        }

        // بتواند هدیه بدهد:
        targetPlayer.getInventory().addItem(targetItem, amount);
        if (currentPlayer.getSpouse() != null) {
            if (currentPlayer.getSpouse().equals(targetPlayer)) {
                currentPlayer.increaseEnergy(50);
                targetPlayer.increaseEnergy(50);
            }
        }



        Message message = new Message(currentPlayer, currentPlayer.getName() + "sent you a gift please " +
                "rate to your gift with a number between 1 - 5");
        targetPlayer.getOrCreateInteractionWith(currentPlayer).addGift(targetItem);
        targetPlayer.addNotification(message);
        return new Result(true, "your gift sent successfully");

    }

    public Result rateGift(Matcher matcher) {
        String giftNumberStr = matcher.group("giftNumber");
        String rateStr = matcher.group("rate");
        int giftNumber = Integer.parseInt(giftNumberStr);
        int rate = Integer.parseInt(rateStr);
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        Player targetPlayer = null;
        int addingScore = (rate - 3) * 30 + 15;
        for (Player player : App.getActiveGame().getPlayers()) {
            for (int x : player.getOrCreateInteractionWith(currentPlayer).getGifts().values()) {
                if (x == giftNumber) {
                    targetPlayer = player;
                }
            }
        }

        if (targetPlayer == null) {
            return new Result(false, "You don't have this gift Id");
        }

        currentPlayer.getOrCreateInteractionWith(targetPlayer).increaseFriendshipLevelScore(addingScore);
        targetPlayer.getOrCreateInteractionWith(currentPlayer).increaseFriendshipLevelScore(addingScore);

        return new Result(true, "Gift delivered successfully!");


    }

    public Result showGiftList() {
        Player current = App.getActiveGame().getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        for (Player player : App.getActiveGame().getPlayers()) {
            if (!player.equals(current)) {
                for (Map.Entry<Item, Integer> entry : player.getOrCreateInteractionWith(current).getGifts().entrySet()) {
                    result.append("Id : ");
                    result.append(entry.getValue());
                    result.append(" ");
                    result.append("item name : ");
                    result.append(entry.getKey().getName());
                    result.append("\n");
                }
            }
        }
        return new Result(true, result.toString());
    }
    public Result hug(Matcher matcher) {
        String username = matcher.group("username");
        Player targetPlayer = App.getActiveGame().getPlayerByUsername(username);
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();

        // سطح دو نباشند
        if (currentPlayer.getOrCreateInteractionWith(targetPlayer).getFriendshipLevel() < 2) {
            return new Result(false, "Friendship level too low! You can't exchange gifts yet.");
        }

        // در نزدیک هم نباشند
        if (!App.getActiveGame().isAdjacent(currentPlayer.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        if (!currentPlayer.getOrCreateInteractionWith(targetPlayer).isHugged()) {
            currentPlayer.getOrCreateInteractionWith(targetPlayer).increaseFriendshipLevelScore(60);
        }
        if (currentPlayer.getSpouse() != null) {
            if (currentPlayer.getSpouse().equals(targetPlayer)) {
                currentPlayer.increaseEnergy(50);
                targetPlayer.increaseEnergy(50);
            }
        }
        return new Result(true, "Hug attack successful! +1 Happiness! ʕ•ᴥ•ʔ");


    }

    public Result showNotifications() {
      return App.getActiveGame().getCurrentPlayer().showNotifications();
    }


    public Result flower(Matcher matcher) {
        String username = matcher.group("username");
        Player player = App.getActiveGame().getCurrentPlayer();
        Player targetPlayer = App.getActiveGame().getPlayerByUsername(username);
        // کنار هم نباشند
        if (!App.getActiveGame().isAdjacent(player.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        //گل در اینونتوری نباشد
        if (player.getInventory().getItemByName("flower") == null) {
            return new Result(false, "You don't have enough of this item in your inventory!");
        }

        // سطح دو نباشند
        if (player.getOrCreateInteractionWith(targetPlayer).getFriendshipLevel() < 2) {
            return new Result(false, "Friendship level too low! You can't exchange gifts yet.");
        }

        Item flower = player.getInventory().getItemByName("flower");
        // گل بدهد
        if (player.getSpouse() != null) {
            if (player.getSpouse().equals(targetPlayer)) {
                player.increaseEnergy(50);
                targetPlayer.increaseEnergy(50);
            }
        }
        targetPlayer.getInventory().addItem(flower, 1);
        player.getInventory().removeItem(flower, 1);
        player.getOrCreateInteractionWith(targetPlayer).setFriendshipLevel(3);
        targetPlayer.getOrCreateInteractionWith(player).setFriendshipLevel(3);
        return new Result(true, "Their cheeks flush pink as they accept the flowers." +
                " 'It's beautiful... thank you!");

    }


    public Result askMarriage(Matcher matcher) {
        String username = matcher.group("username");
        String ringName = matcher.group("ring");
        Player player = App.getActiveGame().getCurrentPlayer();
        Player targetPlayer = App.getActiveGame().getPlayerByUsername(username);

        // کنار هم نباشند
        if (!App.getActiveGame().isAdjacent(player.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        // سطح سه نباشند
        if (player.getOrCreateInteractionWith(targetPlayer).getFriendshipLevel() < 3) {
            return new Result(false, " 'Not enough hearts!");
        }

        // پیشنهاد دهنده دختر باشد
        if (player.getGender().equalsIgnoreCase("female")) {
            return new Result(false, "Tradition in this valley says the groom must do the proposing!");
        }


        // به همجنس پیشنهاد دهد
        if (player.getGender().equalsIgnoreCase(targetPlayer.getGender())) {
            return new Result(false, "This valley doesn’t support same-sex marriage.");
        }

        // حلقه نداشته باشد
        if (player.getInventory().getItemByName(ringName) == null) {
            return new Result(false, "You need a Wedding Ring to propose!");
        }

        // پیام ارسال شد
        Message message = new Message(player, player.getName() +
                "asked you marriage you can accept or reject with 'respond' command");
        targetPlayer.addNotification(message);
        return new Result(true, "Your proposal hangs in the air... " +
                "Their answer will come with time ﮩ٨ـﮩﮩ٨ـ♡ﮩ٨ـﮩﮩ٨ـ");

    }

    public Result handleMarriage(Matcher matcher) {
        String action = matcher.group("action");
        String username = matcher.group("username");
        Player target = App.getActiveGame().getPlayerByUsername(username);
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        if (action.equalsIgnoreCase("accept")) {
            target.setSpouse(currentPlayer);
            currentPlayer.setSpouse(target);
            MarriageRing marriageRing = (MarriageRing) currentPlayer.getInventory().getItemByName("marriage ring");
            target.getInventory().addItem(marriageRing, 1);
            currentPlayer.getInventory().removeItem(marriageRing, 1);
            currentPlayer.getOrCreateInteractionWith(target).setFriendshipLevel(4);
            target.getOrCreateInteractionWith(currentPlayer).setFriendshipLevel(4);
            int totalCoin = currentPlayer.getCoin() + target.getCoin();
            currentPlayer.setCoin(totalCoin / 2);
            target.setCoin(totalCoin / 2);
        } else if (action.equalsIgnoreCase("reject")) {
            currentPlayer.setRejectionCooldown(7);
            currentPlayer.getOrCreateInteractionWith(target).setFriendshipLevel(0);
            currentPlayer.getOrCreateInteractionWith(target).setFriendshipScore(0);
            target.getOrCreateInteractionWith(currentPlayer).setFriendshipScore(0);
            target.getOrCreateInteractionWith(currentPlayer).setFriendshipScore(0);
        }
        return new Result(true, "");
    }

    public Result meetNPC(Matcher matcher) {
        String NPCName = matcher.group("NPCName");
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        NPC targetNPC = App.getActiveGame().getNPCByName(NPCName);
        TimeDate currentDate = TimeDate.getInstance(App.getActiveGame());

        // این NPC اصلا وجود نداشته باشد
        if (targetNPC == null) {
            return new Result(false, "This person seems to exist only in legends... " +
                    "or maybe your imagination?");
        }

        // در نزدیک ۸ خانه ی مجاور NPC باشد
        if (!App.getActiveGame().isAdjacent(currentPlayer.getPosition(), targetNPC.getNPCPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        // بتواند با NPC ارتباط بگیرد
        boolean hadInteractionToday;
        hadInteractionToday = currentPlayer.getOrCreateInteractionWith(targetNPC).isMetToday();
        if (!hadInteractionToday) {
            currentPlayer.getOrCreateInteractionWith(targetNPC).increaseFriendshipLevelScore(20);
            currentPlayer.getOrCreateInteractionWith(targetNPC).setMetToday(true);
        }

        String answer;
        if (targetNPC.getName().equalsIgnoreCase("Sebastian")) {
            answer = targetNPC.getNpcType().getDialogueForSebastian(currentPlayer.
                            getOrCreateInteractionWith(targetNPC).getFriendshipNPCLevel(),
                    currentDate.getSeason(), App.getActiveGame().getWeatherCondition(),
                    currentDate.getHour());
        } else if (targetNPC.getName().equalsIgnoreCase("Abigail")) {
            answer = targetNPC.getNpcType().getDialogueForAbigail(currentPlayer.
                            getOrCreateInteractionWith(targetNPC).getFriendshipNPCLevel(),
                    currentDate.getSeason(), App.getActiveGame().getWeatherCondition(),
                    currentDate.getHour());
        } else if (targetNPC.getName().equalsIgnoreCase("Harvey")) {
            answer = targetNPC.getNpcType().getDialogueForHarvey(currentPlayer.
                            getOrCreateInteractionWith(targetNPC).getFriendshipNPCLevel(),
                    currentDate.getSeason(), App.getActiveGame().getWeatherCondition(),
                    currentDate.getHour());
        } else if (targetNPC.getName().equalsIgnoreCase("Leah")) {
            answer = targetNPC.getNpcType().getDialogueForLeah(currentPlayer.
                            getOrCreateInteractionWith(targetNPC).getFriendshipNPCLevel(),
                    currentDate.getSeason(), App.getActiveGame().getWeatherCondition(),
                    currentDate.getHour());
        } else if (targetNPC.getName().equalsIgnoreCase("Robin")) {
            answer = targetNPC.getNpcType().getDialogueForRobin(currentPlayer.
                            getOrCreateInteractionWith(targetNPC).getFriendshipNPCLevel(),
                    currentDate.getSeason(), App.getActiveGame().getWeatherCondition(),
                    currentDate.getHour());
        } else {
            answer = "HAHAH";
        }

        return new Result(true, answer);

    }

    public Result giftNPC(Matcher matcher) {
        String NPCName = matcher.group("NPCName");
        String giftName = matcher.group("giftName");
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        NPC targetNPC = App.getActiveGame().getNPCByName(NPCName);
        TimeDate currentDate = TimeDate.getInstance(App.getActiveGame());

        // این NPC اصلا وجود نداشته باشد
        if (targetNPC == null) {
            return new Result(false, "This person seems to exist only in legends... " +
                    "or maybe your imagination?");
        }

        // در نزدیک ۸ خانه ی مجاور NPC باشد
        if (!App.getActiveGame().isAdjacent(currentPlayer.getPosition(), targetNPC.getNPCPosition())) {
            return new Result(false, "No one's here to answer you.");
        }

        // اگر ابزار الات باشد
        if (isTool(giftName)) {
            return new Result(false, "You can't gift tools to people. Try something else!");
        }

        //بتواند با ان ارتباط بگیرد
        boolean hadInteractionToday;
        hadInteractionToday = currentPlayer.getOrCreateInteractionWith(targetNPC).isGiftedToday();
        if (!hadInteractionToday) {
            currentPlayer.getOrCreateInteractionWith(targetNPC).increaseFriendshipLevelScore(50);
            currentPlayer.getOrCreateInteractionWith(targetNPC).setMetToday(true);
            // اگر هدیه مورد علاقه ی فرد باشد :
            if (targetNPC.getNpcType().isFavorite(giftName)) {
                currentPlayer.getOrCreateInteractionWith(targetNPC).increaseFriendshipLevelScore(200);
            }
        }

        return new Result(false, "Gift given successfully! They seem pleased!");
    }


    private boolean isTool(String name) {
        if (name.toLowerCase().contains("axe") || name.toLowerCase().contains("hoe") ||
                name.toLowerCase().contains("milk pail") || name.toLowerCase().contains("pickaxe") ||
                name.toLowerCase().contains("scythe") || name.toLowerCase().contains("shear")) {
            return true;
        }
        return false;
    }

    public Result cheatCodeSetFriendshipLevel(Matcher matcher) {
        String targetUsername = matcher.group("username");
        Player target = App.getActiveGame().getPlayerByUsername(targetUsername);
        String levelStr = matcher.group("level");
        int level = Integer.parseInt(levelStr);
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        currentPlayer.setFriendshipLevelWith(level, targetUsername);
        return new Result(true, "level set successfully : "
                + currentPlayer.getOrCreateInteractionWith(target).getFriendshipLevel());
    }

}
