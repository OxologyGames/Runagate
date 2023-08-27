package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oxology.menu.Button;
import com.oxology.screen.Template;

public class Main extends Template {
    private Button playButton;
    private SpriteBatch batch;

    public Main() {
        camera = new OrthographicCamera(1920, 1080);
        camera.translate(1920/2f, 1080/2f);
        camera.update();

        BitmapFont font = new BitmapFont(Gdx.files.internal("font/bahnschriftLight.fnt"));
        font.setColor(1, 1, 1, 1);
        font.getData().scaleX = .1f;
        font.getData().scaleY = .1f;
        playButton = new Button(100, 300, "Play", font, new Button.Action() {
            @Override
            public void onAction() {
                //System.out.println("clicked");
            }
        }, new Button.Action() {
            @Override
            public void onAction() {
                //System.out.println("hover");
            }
        });

        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(0, 1, 0, 1);
        batch.begin();
        playButton.draw(batch);
        batch.end();

        //System.out.println("drawn");

        playButton.update();
    }
}
