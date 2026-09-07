package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.controller.GameController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.graphics.GameScreen;

public class PlayerGraphics {
    private Player player;
    int tileSize;
    private Texture spriteSheet;
    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight, faint, eating;
    private TextureRegion currentFrame;
    private Texture face;
    private TextureRegion simpleTexture;
    private float stateTime = 0f;
    protected float x, y;

    private boolean isEating = false;

    private int playerWidth, playerHeight;

    public PlayerGraphics(Player player, String spritePath, int frameWidth, int frameHeight) {
        this.player = player;
        this.x = player.getPosition().getX();
        this.y = player.getPosition().getY();
        this.tileSize = GameScreen.TILE_SIZE;
        spriteSheet = new Texture(spritePath);
        playerWidth = spriteSheet.getWidth() / 7;
        playerHeight = spriteSheet.getHeight() / 7;
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        simpleTexture = tmp[2][1];

        walkDown = new Animation<>(0.4f, tmp[2]);
        walkLeft = new Animation<>(0.4f, tmp[3]);
        walkRight = new Animation<>(0.4f, tmp[1]);
        walkUp = new Animation<>(0.6f, tmp[0]);

        String pathStr = spritePath.substring(0, spritePath.length() - 4);

        Texture faintSpriteSheet = new Texture(pathStr + "_faint.png");


        TextureRegion[][] faintTmp = TextureRegion.split(faintSpriteSheet, 200, 266);

// Flatten all rows into a single array
        TextureRegion[] faintFrames = new TextureRegion[2 * 2]; // total frames = rows * cols
        int index = 0;
        for (int row = 0; row < faintTmp.length; row++) {
            for (int col = 0; col < faintTmp[row].length; col++) {
                faintFrames[index++] = faintTmp[row][col];
            }
        }

        faint = new Animation<>(0.4f, faintFrames); // adjust speed as needed



        Texture eatingSpriteSheet = new Texture(pathStr + "_eating.png");
        int eatingCols = 2; // adjust to match your eating sprite
        int eatingRows = 2; // adjust to match your eating sprite
        TextureRegion[][] eatingTmp = TextureRegion.split(
            eatingSpriteSheet,
            64,
            64
        );

        TextureRegion[] eatingFrames = new TextureRegion[eatingCols * eatingRows];
        index = 0;
        for (int row = 0; row < eatingRows; row++) {
            for (int col = 0; col < eatingCols; col++) {
                eatingFrames[index++] = eatingTmp[row][col];
            }
        }
        eating = new Animation<>(0.2f, eatingFrames);


        face = new Texture(pathStr + "_face.png");
    }

    public void startEating() {
        isEating = true;
        stateTime = 0f;
    }

    public void startFainting() {
        stateTime = 0f;
    }

    public Texture getFace() {
        return face;
    }

    public void update(float delta, boolean up, boolean down, boolean left, boolean right) {
        if (player.isFainted()) {
            stateTime += delta;
            return;
        }

        if (isEating) {
            stateTime += delta;
            if (eating.isAnimationFinished(stateTime)) {
                isEating = false;
                stateTime = 0f;
            }
            return;
        }

        player.setMoving(false);

        if (up) {
            y += player.getSpeed() * delta;
            player.setCurrentDirection(Direction.UP);
            player.setMoving(true);
        } else if (down) {
            y -= player.getSpeed() * delta;
            player.setCurrentDirection(Direction.DOWN);
            player.setMoving(true);
        }

        if (left) {
            x -= player.getSpeed() * delta;
            player.setCurrentDirection(Direction.LEFT);
            player.setMoving(true);
        } else if (right) {
            x += player.getSpeed() * delta;
            player.setCurrentDirection(Direction.RIGHT);
            player.setMoving(true);
        }

        if (player.isMoving()) stateTime += delta;
        else stateTime = 0f;
    }

    public void render(SpriteBatch batch) {
        if (player.isFainted()) {
            stateTime += Gdx.graphics.getDeltaTime();
            if (faint.isAnimationFinished(stateTime)) {
                currentFrame = faint.getKeyFrames()[faint.getKeyFrames().length - 1];
                App.getActiveGame().nextTurn();
            } else {
                currentFrame = faint.getKeyFrame(stateTime, false);
            }

            playerWidth = currentFrame.getRegionWidth() / 4;
            playerHeight = currentFrame.getRegionHeight() / 4;
            batch.draw(currentFrame,
                x * GameScreen.TILE_SIZE,
                y * GameScreen.TILE_SIZE,
                playerWidth, playerHeight);
            return;
        } else if (isEating) {
            stateTime += Gdx.graphics.getDeltaTime();
            if (eating.isAnimationFinished(stateTime)) {
                isEating = false;
                stateTime = 0f;
            }
            currentFrame = eating.getKeyFrame(stateTime, true);
        } else {
            switch (player.getCurrentDirection()) {
                case UP: currentFrame = walkUp.getKeyFrame(stateTime, true); break;
                case DOWN: currentFrame = walkDown.getKeyFrame(stateTime, true); break;
                case LEFT: currentFrame = walkLeft.getKeyFrame(stateTime, true); break;
                case RIGHT: currentFrame = walkRight.getKeyFrame(stateTime, true); break;
            }
        }

        playerWidth = currentFrame.getRegionWidth() / 2;
        playerHeight = currentFrame.getRegionHeight() / 2;

        batch.draw(currentFrame, x * GameScreen.TILE_SIZE, y * GameScreen.TILE_SIZE, (float) playerWidth, (float) playerHeight);
    }

    public TextureRegion getSimpleTexture() {
        return simpleTexture;
    }

    public void setSimpleTexture(TextureRegion simpleTexture) {
        this.simpleTexture = simpleTexture;
    }

    public Animation<TextureRegion> getEating() {
        return eating;
    }

    public void dispose() {
        spriteSheet.dispose();
    }


}
