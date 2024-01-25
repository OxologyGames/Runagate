package com.oxology.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.Runagate;

public class Toggle extends UIElement {
    private float oldX, oldY;
    private float holdX, holdY;
    private boolean holding, held;
    private boolean heldBefore;

    public Toggle(float x, float y) {
        this.x = x;
        this.y = y;

        held = false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(Runagate.getInstance().getAssetManager().toggle, x, y, 64, 64);
    }

    @Override
    public void update(float delta) {
        if(Math.sqrt(Math.pow(Runagate.getInstance().getX() - (x + 32), 2) + Math.pow(Runagate.getInstance().getY() - (y + 32), 2)) <= 32) {
            if(!heldBefore) {
                holding = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
            }
        } else {
            heldBefore = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        }

        if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            holding = false;
            if(oldX == x && oldY == y && held) {
                System.out.println("HA");
            }
        }

        if(holding) {
            if(!held) {
                oldX = x;
                oldY = y;

                holdX = Runagate.getInstance().getX() - (this.x + 32);
                holdY = Runagate.getInstance().getY() - (this.y + 32);

                held = true;
            }
        } else {
            held = false;
        }

        if(holding) {
            float newX = Runagate.getInstance().getX() - 32 - holdX;
            float newY = Runagate.getInstance().getY() - 32 - holdY;
            x = newX > 0 ? Math.min(newX, 2560-64) : Math.max(newX, 0);
            y = newY > 0 ? Math.min(newY, 1440-64) : Math.max(newY, 0);
        }
    }
}
