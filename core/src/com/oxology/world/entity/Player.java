package com.oxology.world.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oxology.Runagate;
import com.oxology.util.AssetManager;

public class Player extends Entity {
    private static final long serialVersionUID = -3617805264039298622L;

    private float timeElapsed;
    private boolean timeReset;

    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> walkAnimation;
    private final TextureRegion[] jumpTexture;
    private final Animation<TextureRegion> landAnimation;

    private Animation<TextureRegion> currentAnimation;

    private boolean impactLand;
    private boolean movable;

    private int direction;

    public Player(float x, float y) {
        super(x, y);

        timeReset = false;

        AssetManager assetManager = Runagate.getInstance().getAssetManager();
        idleAnimation = new Animation<>(0.1f, new TextureRegion(assetManager.playerIdle).split(48, 48)[0]);
        walkAnimation = new Animation<>(0.1f, new TextureRegion(assetManager.playerWalk).split(48, 48)[0]);
        jumpTexture = new TextureRegion(assetManager.playerJump).split(48, 48)[0];
        landAnimation = new Animation<>(0.1f, new TextureRegion(assetManager.playerLand).split(48, 48)[0]);

        currentAnimation = idleAnimation;

        impactLand = false;
        movable = true;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Gdx.input.isKeyPressed(Input.Keys.A) && movable) {
            move(LEFT);
        } else if(Gdx.input.isKeyPressed(Input.Keys.D) && movable) {
            move(RIGHT);
        } else {
            move(NONE);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            move(UP);
        } else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            move(DOWN);
        } else {
            move(NONE);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            jump();

        timeElapsed+=deltaTime;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = currentAnimation.getKeyFrame(timeElapsed, currentAnimation != landAnimation);

        if(!colliders[0] && getYSpeed() > 2) {
            currentFrame = jumpTexture[0];
        } else if(!colliders[0] && getYSpeed() < -2) {
            currentFrame = jumpTexture[2];
        } else if(!colliders[0]) {
            currentFrame = jumpTexture[1];
        }

        if(colliders[0] && getYSpeed() < -20) {
            impactLand = true;
        }

        if(currentAnimation == landAnimation && currentAnimation.isAnimationFinished(timeElapsed)) {
            impactLand = false;
            movable = true;
        }

        setAnimation();

        if(getXSpeed() > 0)
            direction = 0;
        if(getXSpeed() < 0)
            direction = 1;

        else if(direction == 0 && currentFrame.isFlipX())
            currentFrame.flip(true, false);
        if(direction == 1 && !currentFrame.isFlipX())
            currentFrame.flip(true, false);

        System.out.println(getYSpeed()
        );

        batch.draw(currentFrame, x*8, y*6);
    }

    private void setAnimation() {
        if(getXSpeed() != 0) {
            if(currentAnimation == idleAnimation)
                timeElapsed = 0;
            currentAnimation = walkAnimation;
        } else {
            if(currentAnimation == walkAnimation)
                timeElapsed = 0;
            currentAnimation = idleAnimation;
        }

        if(impactLand) {
            movable = false;
            currentAnimation = landAnimation;

            if(!timeReset) {
                timeElapsed = 0;
                timeReset = true;
            }
        } else {
            if(currentAnimation == landAnimation)
                currentAnimation = idleAnimation;
            timeReset = false;
        }
    }
}
