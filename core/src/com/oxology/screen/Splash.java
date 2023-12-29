package com.oxology.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.oxology.Runagate;

import java.util.Random;

public class Splash extends Template {
    private final Random random;

    public Splash() {
        super(true, true);
        random = new Random();
        setFading(true);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        float color = random.nextFloat(0.7f, 1.0f)*batch.getColor().r;
        batch.setColor(color, color, color, 1);

        batch.begin();
        batch.draw(Runagate.getInstance().getTextureManager().splash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void update(float delta) {
        super.update(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            setStage(4);

        if(getStage() == AFTER_FADE_IN && getTimeElapsed() > 2f)
            setStage(FADE_OUT);

        if(getStage() == AFTER_FADE_OUT)
            Runagate.getInstance().setScreen(Runagate.getInstance().getMainMenuScreen());
    }
}
