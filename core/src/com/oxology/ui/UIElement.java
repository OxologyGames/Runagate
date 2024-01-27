package com.oxology.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class UIElement {
    protected float x, y;

    abstract void draw(SpriteBatch batch);

    abstract void update(float delta);
}
