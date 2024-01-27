package com.oxology.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.oxology.world.block.BrickBlock;
import com.oxology.world.entity.Player;

import java.util.List;
import java.util.UUID;

public class World {
    private final List<Level> levels;
    private Level currentLevel;
    private final UUID id;

    private boolean drawDebug;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera debugCamera;
    private Texture wall = new Texture("level/wall.png");
    private Texture chain = new Texture("level/chain.png");

    public World(List<Level> levels, UUID id) {
        debugCamera = new OrthographicCamera(80, 45);
        debugCamera.translate(40.0f, 22.5f);
        debugCamera.update();

        debugRenderer = new Box2DDebugRenderer();
        this.drawDebug = true;

        this.id = id;

        this.levels = levels;
        if(levels.size() > 0) {
            this.currentLevel = levels.get(0);

            currentLevel.generateWorld();
            if(currentLevel.getPlayer() == null)
                currentLevel.setPlayer(new Player(4, 6));
        }
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        for(int i = 0; i < 80; i++) {
            for(int j = 0; j < 45; j++) {
                if(currentLevel.getTiles()[i][j] instanceof BrickBlock) {
                    batch.draw(wall, i*32, j*32, 32, 32);
                } //else if(currentLevel.getTiles()[i][j] == Tile.CHAIN) {
//                    batch.draw(chain, i*32, j*32, 32, 32);
//                }
            }
        }
        currentLevel.getPlayer().draw(batch);
        batch.end();

        if(drawDebug)
            currentLevel.draw(debugCamera, debugRenderer);
    }

    public void update(float delta) {
        currentLevel.update(delta);
    }

    public void addLevel(Level level) {
        this.levels.add(level);
    }

    public List<Level> getLevels() {
        return levels;
    }

    public UUID getId() {
        return id;
    }
}
