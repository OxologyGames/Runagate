package com.oxology.world.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity {
    public int facing;

    public Player(float x, float y) {
        super(x, y);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);

        float acceleration = ACCELERATION_SPEED;
        if(!colliders[0])
            acceleration = ACCELERATION_AIR_SPEED;
        if(Gdx.input.isKeyPressed(Input.Keys.A) && xSpeed >= -acceleration) {
            if(!onChain)
                xSpeed = -acceleration;
            facing = LEFT;
        } else if(Gdx.input.isKeyPressed(Input.Keys.D) && xSpeed <= acceleration) {
            if(!onChain)
                xSpeed = acceleration;
            facing = RIGHT;
        } else {
            facing = NONE;
            float deceleration = DECELERATION_SPEED;
            if(!colliders[0])
                deceleration = DECELERATION_AIR_SPEED;

            if(Math.abs(xSpeed) <= deceleration) xSpeed = 0.0f;

            if(xSpeed != 0.0f)
                xSpeed += xSpeed > 0.0f ? -deceleration : deceleration;
        }

        if(onChain) {
            if(Gdx.input.isKeyPressed(Input.Keys.W)) {
                ySpeed = CHAIN_SPEED;
            } else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
                ySpeed = -CHAIN_SPEED;
            } else {
                ySpeed = 0;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && (colliders[0] || onChain)) {
            ySpeed = JUMP_SPEED;
            if(onChain) {
                jumpedOffChain = true;
                switch (facing) {
                    case LEFT:
                        xSpeed = -ACCELERATION_SPEED;
                        break;
                    case RIGHT:
                        xSpeed = ACCELERATION_SPEED;
                        break;
                }
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(new Texture("char.png"), x*8, y*6);
    }
}
