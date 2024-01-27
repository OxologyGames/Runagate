package com.oxology.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.oxology.Runagate;

import java.util.Random;

public class Splash extends Template {
    private final Random random;
//    private final Sound sound;

    public Splash() {
        super(true, true);
        random = new Random();
        setFading(true);
//        sound = Gdx.audio.newSound(Gdx.files.internal("splash.mp3"));
//        sound.play();
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        batch.setColor(batch.getColor().mul(random.nextFloat(0.8f, 1.0f)));

        batch.begin();
        batch.draw(Runagate.getInstance().getAssetManager().splash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
