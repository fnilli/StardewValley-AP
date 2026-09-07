package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.BuffType;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.user.Player;

import java.util.TreeMap;

import static com.group16.stardewvalley.model.user.Player.MAXENERGY;

public class GameHUD {
    // -------- GameHUD fields --------
    private final Texture clockTexture;
    private final TextureRegion clockFaceRegion;
    private final TextureRegion clockHandRegion;
    private final TextureRegion springRegion;
    private final TextureRegion summerRegion;
    private final TextureRegion fallRegion;
    private final TextureRegion winterRegion;
    private final TextureRegion sunnyRegion;
    private final TextureRegion rainyRegion;
    private final TextureRegion stormRegion;
    private final TextureRegion snowyRegion;
    private final BitmapFont font;
    private static final int CLOCK_WIDTH = 70;
    private static final int CLOCK_HEIGHT = 60;
    private static final float SCALE = 1.2f;
    private Texture buffIcon;

    // -------- EnergyBarHUD fields --------
    private final TreeMap<Integer, Texture> energyTextures = new TreeMap<>();

    public GameHUD() {
        // GameHUD constructor
        clockTexture = new Texture("sprites/Clock.png");

        BitmapFont originalFont = GameAssetManager.getGameAssetManager().getSkin().getFont("font");
        font = new BitmapFont(originalFont.getData().fontFile, originalFont.getRegion(), false);
        font.getData().setScale(0.5f);
        font.setColor(Color.BLACK);

        clockFaceRegion = new TextureRegion(clockTexture, 0, 0, 70, 60);
        clockHandRegion = new TextureRegion(clockTexture, 71, 0, 9, 59);
        springRegion = new TextureRegion(clockTexture, 80, 0, 12, 8);
        summerRegion = new TextureRegion(clockTexture, 93, 0, 12, 8);
        fallRegion = new TextureRegion(clockTexture, 106, 0, 12, 8);
        winterRegion = new TextureRegion(clockTexture, 80, 9, 12, 8);
        sunnyRegion = new TextureRegion(clockTexture, 119, 9, 12, 8);
        rainyRegion = new TextureRegion(clockTexture, 106, 9, 12, 8);
        stormRegion = new TextureRegion(clockTexture, 119, 18, 12, 8);
        snowyRegion = new TextureRegion(clockTexture, 93, 18, 12, 8);

        // EnergyBarHUD constructor
        int[] levels = {0, 2, 3, 4, 6, 8, 10, 13, 15, 18, 20, 23, 25, 27, 30, 33, 35, 37, 40, 43,
            45, 47, 50, 53, 55, 57, 60, 61, 63, 65, 67, 70, 73, 75, 80, 82, 84, 86,
            90, 92, 94, 96, 98, 100};

        for (int level : levels) {
            energyTextures.put(level, new Texture("EnergyBar/" + level + ".png"));
        }
    }

    // Render method for both HUDs
    public void render(SpriteBatch batch, float clockX, float clockY, float energyBarX, float energyBarY) {
        renderClock(batch, clockX, clockY);
        renderEnergyBar(batch, energyBarX, energyBarY);
    }

    // Draw the clock HUD
    private void renderClock(SpriteBatch batch, float screenX, float screenY) {
        Player player = App.getActiveGame().getCurrentPlayer();

        float scaledWidth = CLOCK_WIDTH * SCALE;
        float scaledHeight = CLOCK_HEIGHT * SCALE;

        batch.draw(clockFaceRegion, screenX - 10, screenY - 5, scaledWidth, scaledHeight);

        float originX = screenX + scaledWidth / 2f - 20;
        float originY = screenY + scaledHeight / 2f + 12;

        float rotation = 5 * 30f; // Temporary fixed rotation

        batch.draw(clockHandRegion, originX - 3, originY - scaledHeight / 2,
            3, scaledHeight / 2, 10, CLOCK_HEIGHT, SCALE, SCALE, rotation);

        font.draw(batch, TimeDate.getInstance(App.getActiveGame()).getTime(), screenX + 30, screenY + 32);
        font.draw(batch, TimeDate.getInstance(App.getActiveGame()).getDayOfWeek() + ". " +
            TimeDate.getInstance(App.getActiveGame()).getDay() , screenX + 25, screenY + 59);

        switch (TimeDate.getInstance(App.getActiveGame()).getSeason()) {
            case Spring:
                batch.draw(springRegion, screenX + 25, screenY + 38, SCALE * 12, SCALE * 8);
                break;
            case Summer:
                batch.draw(summerRegion, screenX + 25, screenY + 38, SCALE * 12, SCALE * 8);
                break;
            case Fall:
                batch.draw(fallRegion, screenX + 25, screenY + 38, SCALE * 12, SCALE * 8);
                break;
            case Winter:
                batch.draw(winterRegion, screenX + 25, screenY + 38, SCALE * 12, SCALE * 8);
                break;
        }

        if (App.getActiveGame().getWeatherCondition() == null) {
            batch.draw(sunnyRegion, screenX + 54, screenY + 38, SCALE * 12, SCALE * 8);
        } else {
            switch (App.getActiveGame().getWeatherCondition()) {
                case SUNNY:
                    batch.draw(sunnyRegion, screenX + 54, screenY + 38, SCALE * 12, SCALE * 8);
                    break;
                case RAINY:
                    batch.draw(rainyRegion, screenX + 54, screenY + 38, SCALE * 12, SCALE * 8);
                    break;
                case STORM:
                    batch.draw(stormRegion, screenX + 54, screenY + 38, SCALE * 12, SCALE * 8);
                    break;
                case SNOWY:
                    batch.draw(snowyRegion, screenX + 54, screenY + 38, SCALE * 12, SCALE * 8);
                    break;
            }
        }

        String money;
        if (player == null || player.getCoin() == 0) {
            money = "000";
        } else {
            money = String.valueOf(player.getCoin());
        }

        float boxX = screenX - 4;
        float boxY = screenY + 8;
        float boxWidth = 70f;

        GlyphLayout layout = new GlyphLayout(font, money);
        float textWidth = layout.width;
        float textX = boxX + boxWidth - textWidth;
        font.draw(batch, layout, textX, boxY);

        BuffType buff = player.getBuffer();
        if (buff != BuffType.NONE) {
            buffIcon = GameAssetManager.getGameAssetManager().getTexture(buff.getTexturePath());
            batch.draw(buffIcon, screenX - 50, screenY + 20, 30, 30);
        }
    }

    // Draw the energy bar HUD
    private void renderEnergyBar(SpriteBatch batch, float x, float y) {
        double energy = App.getActiveGame().getCurrentPlayer().getEnergy();
//        System.out.println("energy: " + energy);
        int percent = (int) Math.round((energy / MAXENERGY) * 100);
        percent = Math.min(100, Math.max(0, percent));

        Integer closest = energyTextures.floorKey(percent);
        if (closest == null) closest = 0;

        Texture bar = energyTextures.get(closest);

        // Original size is 35x201, so we'll scale it by 0.5
        float scale = 0.4f;
        float width = bar.getWidth() * scale;
        float height = bar.getHeight() * scale;

        // Draw scaled
        batch.draw(bar, x, y, width, height);
    }


    public void dispose() {
        clockTexture.dispose();
        font.dispose();
        for (Texture t : energyTextures.values()) {
            t.dispose();
        }
    }
}
