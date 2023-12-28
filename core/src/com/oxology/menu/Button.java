package com.oxology.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.screen.Template;

public class Button {
    private String text;
    private float x, y;
    private float width, heigth;
    private BitmapFont font;
    private Action click;
    private boolean hover;
    private Texture button, buttonClick;
    private int size;

    private Template screenTemplate;

    public interface Action {
        void onAction();
    };

    public Button(float x, float y, int size, String text, BitmapFont font, Action click, Template screenTemplate) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
        this.click = click;
        this.hover = false;
        switch (size) {
            case 1:
                this.button = new Texture("button_long.png");
                this.buttonClick = new Texture("button_long_click.png");
                break;
            case 2:
                this.button = new Texture("button_play.png");
                this.buttonClick = new Texture("button_play_click.png");
                break;
            default:
                this.button = new Texture("button.png");
                this.buttonClick = new Texture("button_click.png");
                break;
        }

        this.size = size;

        GlyphLayout layout = new GlyphLayout(font, text);
        this.width = layout.width;
        this.heigth = layout.height;

        this.screenTemplate = screenTemplate;
    }

    public void draw(SpriteBatch batch) {
        if(hover) {
            batch.draw(buttonClick, x, y);
        } else {
            batch.draw(button, x, y);
        }
        int halfWidth = size == 0 ? 25 : 50;
        font.setColor(batch.getColor());
        font.draw(batch, text, x+halfWidth-width/2, y-1);
    }

    public void update() {
        if((screenTemplate.getX() >= x && screenTemplate.getX() < x + button.getWidth()) && (screenTemplate.getY() >= y && screenTemplate.getY() < y + button.getHeight())) {
            hover = true;

            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                click.onAction();
            }
        } else {
            hover = false;
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
