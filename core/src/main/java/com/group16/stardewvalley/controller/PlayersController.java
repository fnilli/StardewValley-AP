package com.group16.stardewvalley.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.graphics.GameScreen;
import java.util.List;

public class PlayersController {
    private List<Player> players;

    public PlayersController() {
        players = App.getActiveGame().getPlayers();
    }

    public void update(float delta) {
        for (Player player : players) {
            //player.update(delta);
        }
    }

    public void render() {
        SpriteBatch batch = Main.getBatch();
        if (GameScreen.showMiniMap) {
            for (Player player1 : players) {
                batch.draw(player1.getPlayerGraphics().getFace(), player1.getX() * GameScreen.TILE_SIZE, player1.getY() * GameScreen.TILE_SIZE,
                    player1.getPlayerGraphics().getFace().getWidth() * 4, player1.getPlayerGraphics().getFace().getHeight() * 4);
            }
        } else {
            for (Player player1 : players) {
                player1.getPlayerGraphics().render(batch);
            }
        }
    }

    public void move(Player player, float speed, boolean up, boolean down, boolean left, boolean right) {
        player.getPlayerGraphics().update(speed, up, down, left, right);
    }
}

