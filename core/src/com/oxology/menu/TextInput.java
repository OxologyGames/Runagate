package com.oxology.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.Runagate;

public class TextInput {
    private float x, y;
    private float width, heigth;
    private float maxWidth, currentWidth;

    private String text;
    private BitmapFont font;
    private GlyphLayout textLayout;

    public TextInput(float x, float y, int width, int height, BitmapFont font) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = height;
        this.maxWidth = width + 20;
        this.currentWidth = width;

        this.text = "";
        this.font = font;

        this.textLayout = new GlyphLayout(font, text);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(Runagate.getInstance().getAssetManager().pixel, x - (currentWidth-width)/2f, y, currentWidth, heigth);
        font.draw(batch, text, x + (width/2f) - (textLayout.width/2f), (y + textLayout.height) + (heigth/2f) - (textLayout.height/2f));
    }

    public void update(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            for(int i = 29; i <= 54; i++) {
                if(Gdx.input.isKeyJustPressed(i)) {
                    text += Input.Keys.toString(i);
                    textLayout = new GlyphLayout(font, text);
                }
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                text += " ";
                textLayout = new GlyphLayout(font, text);
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
                if(!text.isEmpty()) {
                    text = text.substring(0, text.length()-1);
                    textLayout = new GlyphLayout(font, text);
                }
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
