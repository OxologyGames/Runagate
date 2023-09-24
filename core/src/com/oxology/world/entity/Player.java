package com.oxology.world.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity {
    public Player(float x, float y) {
        super(x, y);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            xSpeed = -5f;
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            xSpeed = 5f;
        } else {
            if(Math.abs(xSpeed) <= 0.2) xSpeed = 0;

            if(xSpeed != 0)
                xSpeed += xSpeed > 0 ? -0.2f : 0.2f;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            ySpeed = 5f;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(new Texture("char.png"), x*8, y*6);
    }
}
