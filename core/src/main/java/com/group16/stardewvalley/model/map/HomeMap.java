package com.group16.stardewvalley.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.graphics.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class HomeMap {
    private final Texture backgroundTexture;
    private final Sprite backgroundSprite;

    private final List<Rectangle> wallBounds;
    private Rectangle fridgeBounds;
    private final Rectangle bedBounds;

    private final Player player;

    public HomeMap(Player player) {
        backgroundTexture = new Texture("InsideHouse/house.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setPosition(0, 0);

        wallBounds = new ArrayList<>();
        defineWallBounds();

        bedBounds = new Rectangle(564, 70, 83, 91);     // مکان و اندازه تخت

        this.player = player;

    }

    private void defineWallBounds() {
        // اینجا دیوارهای خانه را تعریف می‌کنیم
        // به عنوان مثال چهار طرف خانه + وسط‌ها
        wallBounds.add(new Rectangle(0, 290, 232, 40));
        wallBounds.add(new Rectangle(0, 10 ,799, 95));
        wallBounds.add(new Rectangle(261, 290, 539, 40));
        //wallBounds.add(new Rectangle(608, 0, 32, 480));   // راست

        // دیوار وسطی یا تزئینی
        //wallBounds.add(new Rectangle(200, 300, 240, 16));
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float imageWidth = backgroundTexture.getWidth();
        float imageHeight = backgroundTexture.getHeight();

        float scaleX = screenWidth / imageWidth;
        float scaleY = screenHeight / imageHeight;

        float scale = Math.min(scaleX, scaleY);

        float newWidth = imageWidth * scale;
        float newHeight = imageHeight * scale;

        float x = (screenWidth - newWidth) / 2f;
        float y = (screenHeight - newHeight) / 2f;

        backgroundSprite.setSize(newWidth, newHeight);
        backgroundSprite.setPosition(x, y);

        float fridgeX = 270;
        float fridgeY = 380 + y;
        float fridgeWidth = 50;
        float fridgeHeight = 100;

        fridgeBounds = new Rectangle(fridgeX, fridgeY, fridgeWidth, fridgeHeight);


        backgroundSprite.draw(batch);
        batch.draw(player.getPlayerGraphics().getSimpleTexture(), 450, 250);
    }


    public boolean isWallCollision(Rectangle playerBounds) {
        for (Rectangle wall : wallBounds) {
            if (playerBounds.overlaps(wall)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOnFridge(Vector2 clickPos) {
        return fridgeBounds.contains(clickPos);
    }

    public boolean isOnBed(Vector2 playerCenterPos) {
        return bedBounds.contains(playerCenterPos);
    }

    public Rectangle getFridgeBounds() {
        return fridgeBounds;
    }

    public Rectangle getBedBounds() {
        return bedBounds;
    }

    public List<Rectangle> getWallBounds() {
        return wallBounds;
    }

    public void dispose() {
        backgroundTexture.dispose();
    }
}
