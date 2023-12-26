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
            move(LEFT);
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            move(RIGHT);
        } else {
            move(NONE);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            move(UP);
        } else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            move(DOWN);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            jump();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(new Texture("char.png"), x*8, y*6);
    }
}
