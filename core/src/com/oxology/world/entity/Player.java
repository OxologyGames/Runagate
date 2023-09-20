package com.oxology.world.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity {
    public Player(int x, int y) {
        super(x, y);
    }

    public void update(float deltaTime) {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            x-=200f*deltaTime;
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            x+=200f*deltaTime;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(new Texture(""), x, y);
    }
}
