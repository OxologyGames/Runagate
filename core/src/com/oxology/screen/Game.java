package com.oxology.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.oxology.Runagate;
import com.oxology.world.GameObject;
import com.oxology.world.Level;
import com.oxology.world.Tile;
import com.oxology.world.entity.Player;

import java.util.List;

public class Game extends Template {
    private OrthographicCamera levelCamera;
    private SpriteBatch levelBatch;
    private List<Level> levels;

    private Box2DDebugRenderer debugRenderer;


    //temp
    private Texture wall;
    private Texture chain;

    public Game(List<Level> levels) {
        super();

        levelCamera = new OrthographicCamera(320, 180);
        levelCamera.translate(320/2f, 180/2f);
        levelCamera.update();

        debugRenderer = new Box2DDebugRenderer();

        levelBatch = new SpriteBatch();

        this.levels = levels;
        levels.get(0).generateWorld();
        if(levels.get(0).getPlayer() == null)
            levels.get(0).setPlayer(new Player(4, 6));

        wall = new Texture("level/wall.png");
        chain = new Texture("level/chain.png");
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        levelBatch.begin();
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 30; j++) {
                if(levels.get(0).getTiles()[i][j] == Tile.WALL) {
                    levelBatch.draw(wall, i*8, j*6);
                } else if(levels.get(0).getTiles()[i][j] == Tile.CHAIN) {
                    levelBatch.draw(chain, i*8, j*6);
                }
            }
        }
        levels.get(0).getPlayer().draw(levelBatch);
        //levelBatch.draw(new Texture("level.png"), 0, 0);
        levelBatch.end();

        levels.get(0).draw(camera, debugRenderer);

        batch.begin();
        batch.end();
    }

    public void update(float deltaTime) {
        levelCamera.update();
        levelBatch.setProjectionMatrix(levelCamera.combined);

        levels.get(0).update(deltaTime);
    }
}
