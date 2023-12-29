package com.oxology.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.Runagate;

public class Template implements Screen {
    public static final int BEFORE_FADE_IN = 0;
    public static final int FADE_IN = 1;
    public static final int AFTER_FADE_IN = 2;
    public static final int FADE_OUT = 3;
    public static final int AFTER_FADE_OUT = 4;

    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    protected int viewportWidth, viewportHeight;

    private int stage;
    private float timeElapsed;
    private float visibility;
    private boolean fading;
    private boolean fadeOut;

    public Template() {
        updateViewport(Runagate.getInstance().getResX(), Runagate.getInstance().getResY());
        batch = new SpriteBatch();
    }

    public Template(boolean fadeIn, boolean fadeOut) {
        this();

        if(fadeIn) {
            this.visibility = 0;
            this.stage = BEFORE_FADE_IN;
        } else if(fadeOut) {
            this.visibility = 1;
            this.stage = FADE_OUT;
        }

        this.fadeOut = fadeOut;

        batch.setColor(visibility, visibility, visibility, 1);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        update(delta);
    }

    public void update(float delta) {
        if(fading) {
            switch (stage) {
                case BEFORE_FADE_IN:
                    if(timeElapsed > 0.5f) {
                        stage++;
                        timeElapsed = 0;
                    }
                    break;
                case FADE_IN:
                    visibility += delta*0.5f;
                    if(timeElapsed > 2f) {
                        stage++;
                        timeElapsed = 0;
                    }
                    break;
                case FADE_OUT:
                    visibility -= delta*0.5f;
                    if(timeElapsed > 2f) {
                        stage++;
                        timeElapsed = 0;
                    }
                    break;
            }
            batch.setColor(visibility, visibility, visibility, 1);

            if(!fadeOut && stage == AFTER_FADE_IN)
                fading = false;
        }

        timeElapsed+=delta;
    }

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

    public float getTimeElapsed() {
        return timeElapsed;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
        this.timeElapsed = 0;
    }

    public void setFading(boolean fading) {
        this.fading = fading;
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
