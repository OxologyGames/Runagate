package com.oxology.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Template implements Screen {
    protected OrthographicCamera camera;
    protected SpriteBatch batch;

    public Template() {
        camera = new OrthographicCamera(640, 360);
        camera.translate(640/2f, 360/2f);
        camera.update();

        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        camera = new OrthographicCamera(640, 360);
        camera.translate(640/2f, 360/2f);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
