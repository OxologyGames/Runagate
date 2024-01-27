package com.oxology.screen;

import com.oxology.world.World;


public class Game extends Template {
    private final World world;

    public Game(World world) {
        super();

        this.world = world;
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        world.draw(batch);
    }

    public void update(float deltaTime) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        world.update(deltaTime);
    }
}
