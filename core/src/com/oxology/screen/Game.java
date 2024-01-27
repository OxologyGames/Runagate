package com.oxology.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.oxology.world.Level;
import com.oxology.world.Tile;
import com.oxology.world.entity.Player;

import java.util.List;

public class Game extends Template {
    private List<Level> levels;

    private Box2DDebugRenderer debugRenderer;

    private OrthographicCamera debugCamera;

    //TODO
    private Texture wall;
    private Texture chain;

    public Game(List<Level> levels) {
        super();

        debugCamera = new OrthographicCamera(80, 45);
        debugCamera.translate(40, 22.5f);
        debugCamera.update();

        debugRenderer = new Box2DDebugRenderer();

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

        batch.begin();
        for(int i = 0; i < 80; i++) {
            for(int j = 0; j < 45; j++) {
                if(levels.get(0).getTiles()[i][j] == Tile.WALL) {
                    batch.draw(wall, i*32, j*32, 32, 32);
                } else if(levels.get(0).getTiles()[i][j] == Tile.CHAIN) {
                    batch.draw(chain, i*32, j*32, 32, 32);
                }
            }
        }
        levels.get(0).getPlayer().draw(batch);
        batch.end();

        levels.get(0).draw(debugCamera, debugRenderer);
    }

    public void update(float deltaTime) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        levels.get(0).update(deltaTime);
    }
}
