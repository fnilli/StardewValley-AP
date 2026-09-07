package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;

public class GameData {
    public User user;
    public Player player;
    public Game game;

    // Default constructor is needed for Jackson
    public GameData() {}

    public GameData(User user, Player player, Game game) {
        this.user = user;
        this.player = player;
        this.game = game;
    }
}
