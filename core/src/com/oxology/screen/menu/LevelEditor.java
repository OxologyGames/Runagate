package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.screen.Template;
import com.oxology.world.Tile;

public class LevelEditor extends Template {
    private OrthographicCamera levelCamera;
    private SpriteBatch levelBatch;
    private Tile[][] tiles;

    private Texture border;

    private Texture airTexture, wallTexture;
    private Texture cursor;
    private int cursorX, cursorY;

    public LevelEditor(int viewportWidth, int viewportHeight) {
        super(viewportWidth, viewportHeight);

        levelCamera = new OrthographicCamera(352, 198);
        levelCamera.translate(352/2f, 198/2f);
        levelCamera.update();

        levelBatch = new SpriteBatch();

        this.tiles = new Tile[40][30];
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 30; j++) {
                tiles[i][j] = Tile.AIR;
            }
        }

        this.border = new Texture("level/levelBorder.png");

        this.airTexture = new Texture("level/air.png");
        this.wallTexture = new Texture("level/wall.png");

        this.cursor = new Texture("level/cursor.png");
    }

    @Override
    public void render(float deltaTime) {
        levelCamera.update();
        levelBatch.setProjectionMatrix(levelCamera.combined);

        levelBatch.begin();
        levelBatch.draw(border, 15, 8);

        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 30; j++) {
                Texture texture = airTexture;
                if(tiles[i][j] == Tile.WALL) texture = wallTexture;
                levelBatch.draw(texture, 16+i*8, 9+j*6);
            }
        }

        levelBatch.draw(cursor, 16+cursorX*8, 9+cursorY*6);
        levelBatch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            cursorY++;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            cursorY--;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            cursorX++;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            cursorX--;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(tiles[cursorX][cursorY] == Tile.AIR)
                tiles[cursorX][cursorY] = Tile.WALL;
            else
                tiles[cursorX][cursorY] = Tile.AIR;
        }
    }
}
