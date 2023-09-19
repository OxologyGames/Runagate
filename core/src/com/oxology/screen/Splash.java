package com.oxology.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.oxology.Runagate;
import com.oxology.screen.menu.WorldEditor;

public class Splash extends Template {
    private Texture splash;
    private float timeElapsed;
    private float visibility;
    private int stage;

    public Splash(Runagate game) {
        super(game);
        this.splash = new Texture("splash.png");
        this.visibility = 0;
        this.stage = 0;

        batch.setColor(visibility, visibility, visibility, 1);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        batch.begin();
        batch.draw(splash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void update(float deltaTime) {
        switch (stage) {
            case 0:
                if(timeElapsed > 0.5f) {
                    stage++;
                    timeElapsed = 0;
                }
                break;
            case 1:
                visibility += deltaTime*0.8f;
                if(timeElapsed > 1) {
                    stage++;
                    timeElapsed = 0;
                }
                break;
            case 2:
                if(timeElapsed > 1.5f) {
                    stage++;
                    timeElapsed = 0;
                }
                break;
            case 3:
                visibility -= deltaTime*0.8f;
                if(timeElapsed > 1) {
                    stage++;
                    timeElapsed = 0;
                }
                break;
            case 4:
                game.setScreen(new WorldEditor(game));
                break;
        }
        batch.setColor(visibility, visibility, visibility, 1);

        timeElapsed+=deltaTime;
    }
}
