package com.oxology.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.Runagate;
import com.oxology.world.entity.Player;

public class Game extends Template {
    private OrthographicCamera levelCamera;
    private SpriteBatch levelBatch;
    private Player player;

    public Game(Runagate game) {
        super(game);

        levelCamera = new OrthographicCamera(320, 180);
        levelCamera.translate(320/2f, 180/2f);
        levelCamera.update();

        levelBatch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        levelCamera.update();
        levelBatch.setProjectionMatrix(levelCamera.combined);

        levelBatch.begin();
        levelBatch.draw(new Texture("level.png"), 0, 0);

        levelBatch.end();

        batch.begin();
        batch.end();
    }
}
