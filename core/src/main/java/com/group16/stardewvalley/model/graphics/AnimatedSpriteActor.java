package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.Gdx;

public class AnimatedSpriteActor extends Actor {
    private final Animation<TextureRegion> animation;
    private float stateTime = 0f;

    public AnimatedSpriteActor(Texture spriteSheet, int frameWidth, int frameHeight, int rowIndex, float frameDuration) {
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, frameWidth, frameHeight);

        // Only take first 4 frames of the specified row
        TextureRegion[] rowFrames = new TextureRegion[4];
        System.arraycopy(tmp[rowIndex], 0, rowFrames, 0, 4);

        this.animation = new Animation<>(frameDuration, rowFrames);
        this.animation.setPlayMode(Animation.PlayMode.LOOP);

        setSize(frameWidth * 4f, frameHeight * 4f); // adjust size as needed
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }
}
