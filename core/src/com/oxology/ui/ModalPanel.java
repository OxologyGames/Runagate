package com.oxology.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ModalPanel extends Panel {
    public ModalPanel(float x, float y, float width) {
        super(x, y, width);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(new Texture(""), x, y, 600, 400);
        super.draw(batch);
    }

    public void setState(int state) {
        this.state = state;
    }
}
