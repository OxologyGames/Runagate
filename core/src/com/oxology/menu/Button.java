package com.oxology.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
    private String text;
    private float x, y;
    private float width, heigth;
    private BitmapFont font;
    private Action click;
    private Action hover;

    public interface Action {
        void onAction();
    };

    public Button(float x, float y, String text, BitmapFont font, Action click, Action hover) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
        this.click = click;
        this.hover = hover;

        GlyphLayout layout = new GlyphLayout(font, text);
        this.width = layout.width;
        this.heigth = layout.height;
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, text, x, y);
    }

    public void update() {
        if((Gdx.input.getX() >= x && Gdx.input.getX() < x + width) && (Gdx.input.getY() >= x && Gdx.input.getY() < y + heigth)) {
            hover.onAction();

            if(Gdx.input.isTouched()) {
                click.onAction();
            }
        }
    }
}