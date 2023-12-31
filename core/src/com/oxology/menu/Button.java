package com.oxology.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.Runagate;

public class Button {
    private float x, y;
    private float width, heigth;
    private float maxWidth, currentWidth;

    private String text;
    private BitmapFont font;
    private GlyphLayout textLayout;

    private Action click;

    public interface Action {
        void onAction();
    };

    public Button(float x, float y, int width, int height, String text, BitmapFont font, Action click) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = height;
        this.maxWidth = width + 20;
        this.currentWidth = width;

        this.text = text;
        this.font = font;
        this.click = click;

        this.textLayout = new GlyphLayout(font, text);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(Runagate.getInstance().getTextureManager().pixel, x - (currentWidth-width)/2f, y, currentWidth, heigth);
        font.draw(batch, text, x + (width/2f) - (textLayout.width/2f), (y + textLayout.height) + (heigth/2f) - (textLayout.height/2f));
    }

    public void update(float delta) {
        if((Runagate.getInstance().getX() >= x && Runagate.getInstance().getX() < x + width) &&
                        (Runagate.getInstance().getY() >= y && Runagate.getInstance().getY() < y + heigth)) {
            if(currentWidth + 150*delta < maxWidth) {
                currentWidth += 150*delta;
            } else {
                currentWidth = maxWidth;
            }

            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                click.onAction();
            }
        } else {
            if(currentWidth - 150*delta > width) {
                currentWidth -= 150*delta;
            } else {
                currentWidth = width;
            }
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public String getText() {
        return text;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setY(float y) {
        this.y = y;
    }
}
