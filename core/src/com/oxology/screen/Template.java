package com.oxology.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.Runagate;

public class Template implements Screen {
    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    protected int viewportWidth, viewportHeight;
    protected Runagate game;

    public Template(Runagate game) {
        updateViewport(game.getResX(), game.getResY());
        batch = new SpriteBatch();
        this.game = game;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {}

    @Override
    public void resize(int width, int height) {
        updateViewport(viewportWidth, viewportHeight);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void updateViewport(int viewportWidth, int viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.camera = new OrthographicCamera(viewportWidth, viewportHeight);
        this.camera.translate(viewportWidth/2f, viewportHeight/2f);
        this.camera.update();
    }

    public int getX() {
        float prop = (float) Gdx.graphics.getBackBufferWidth() / viewportWidth;
        return (int) (Gdx.input.getX() / prop);
    }

    public int getY() {
        float prop = (float) Gdx.graphics.getBackBufferHeight() / viewportHeight;
        return (int) (Gdx.input.getY() / prop);
    }
}
