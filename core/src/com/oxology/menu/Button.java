package com.oxology.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
    private Texture button, buttonClick;

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
        this.button = new Texture("button.png");
        this.buttonClick = new Texture("buttonClick.png");

        GlyphLayout layout = new GlyphLayout(font, text);
        this.width = layout.width;
        this.heigth = layout.height;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(button, x, y);
        font.draw(batch, text, x+25-width/2, y-1);
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
