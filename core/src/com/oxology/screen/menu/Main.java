package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oxology.menu.Button;
import com.oxology.screen.Template;

public class Main extends Template {
    private Button playButton;
    private SpriteBatch batch;

    public Main() {
        super(2560, 1440);
        BitmapFont font = new BitmapFont(Gdx.files.internal("font/bahnschrift.fnt"));
        font.setColor(1, 1, 1, 1);
        font.getData().scaleX = .3f;
        font.getData().scaleY = .3f;
        playButton = new Button(100, 300, "Play", font, new Button.Action() {
            @Override
            public void onAction() {
                //System.out.println("clicked");
            }
        }, this);

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

        playButton.update();
    }
}
