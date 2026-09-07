package com.group16.stardewvalley.controller.shops;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.user.Player;

import java.util.regex.Matcher;

public class ShopCheatcodeController {
    private final Game game;

    public ShopCheatcodeController() {
        this.game = App.getActiveGame();
    }

    private Player currentPlayer = App.getActiveGame().getCurrentPlayer();

    public Result cheatAdd(Matcher matcher) {
        String countStr = matcher.group("count");
        int count = Integer.parseInt(countStr);
        currentPlayer.increaseCoin(count);
        return new Result(true, "Coin added successfully =) Your new coin: " + currentPlayer.getCoin());
    }

}
