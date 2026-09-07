package com.group16.stardewvalley.view;

import com.badlogic.gdx.Screen;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.view.graphics.GameScreen;

import java.util.Scanner;

public class AppView implements Screen {

    private final Main game;

    public AppView(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Scanner scanner = new Scanner(System.in);
        // معادل حلقه‌ی do-while قبلی
        App.getCurrentMenu().checkCommand(scanner); // اگر هنوز Scanner استفاده می‌کنی، باید UI بشه
        if (App.getActiveGame() != null) {

            game.setScreen(GameScreen.getGameScreen());

        }

        if (App.getCurrentMenu() == Menu.ExitMenu) {
            GameAssetManager.getGameAssetManager().dispose();
            TileTextureManager.getTileTextureManager().dispose();
            // بسته شدن بازی
            System.out.println("Exiting...");
            // در آینده بهتره exit رو کنترل کنی
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
