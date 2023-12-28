package com.oxology.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.oxology.Runagate;

import java.util.Random;

public class Splash extends Template {
    private Texture splash;
    private Random random;

    public Splash() {
        super(true, true);
        random = new Random();

        this.splash = new Texture("splash.png");
        setFading(true);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        float color = random.nextFloat(0.7f, 1.0f)*batch.getColor().r;
        batch.setColor(color, color, color, 1);

        batch.begin();
        batch.draw(splash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void update(float delta) {
        super.update(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            setStage(4);

        if(getStage() == 4)
            Runagate.getInstance().setScreen(Runagate.getInstance().getMainMenuScreen());
    }
}
