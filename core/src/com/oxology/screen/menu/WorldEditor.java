package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.menu.Button;
import com.oxology.screen.Template;
import com.oxology.world.Level;
import com.oxology.world.Tile;

public class WorldEditor extends Template {
    private OrthographicCamera levelCamera;
    private SpriteBatch levelBatch;
    private Tile[][] tiles;

    private Texture border;

    private Texture airTexture, wallTexture;
    private Texture cursor;
    private int cursorX, cursorY;

    private Button saveBtn;

    public WorldEditor(int viewportWidth, int viewportHeight) {
        super(viewportWidth, viewportHeight);

        levelCamera = new OrthographicCamera(384, 216);
        levelCamera.translate(384/2f, 216/2f);
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

        this.cursor = new Texture("level/levelBorder2.png");

        BitmapFont font = new BitmapFont(Gdx.files.internal("font/PressStart2P.fnt"));
        font.setColor(Color.WHITE);
        font.getData().scaleX = .09f;
        font.getData().scaleY = .09f;
        this.saveBtn = new Button(332, 189, "Save", font, new Button.Action() {
            @Override
            public void onAction() {
                saveLevel();
            }
        }, this);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        levelBatch.begin();
        levelBatch.draw(border, 8, 17);

        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 30; j++) {
                Texture texture = airTexture;
                if(tiles[i][j] == Tile.WALL) texture = wallTexture;
                levelBatch.draw(texture, 9+i*8, 18+j*6);
            }
        }

        levelBatch.draw(cursor, 9+cursorX*8, 18+cursorY*6);
        saveBtn.draw(levelBatch);
        levelBatch.end();
    }

    private void update(float deltaTime) {
        levelCamera.update();
        levelBatch.setProjectionMatrix(levelCamera.combined);

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && cursorY < 29) {
            cursorY++;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && cursorY > 0) {
            cursorY--;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && cursorX < 39) {
            cursorX++;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && cursorX > 0) {
            cursorX--;
        }

        if(getX() > 8 && getX() < 328 && getY() > 17 && getY() < 197) {
            cursorX = (getX()-9)/8;
            cursorY = (getY()-18)/6;

            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    tiles[cursorX][cursorY] = Tile.WALL;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(tiles[cursorX][cursorY] == Tile.AIR)
                tiles[cursorX][cursorY] = Tile.WALL;
            else
                tiles[cursorX][cursorY] = Tile.AIR;
        }



        saveBtn.update();
    }

    private void saveLevel() {
        Level level = new Level(tiles);
    }

    @Override
    public int getX() {
        float prop = (float) Gdx.graphics.getWidth() / 384;
        return (int) (Gdx.input.getX() / prop);
    }

    @Override
    public int getY() {
        float prop = (float) Gdx.graphics.getHeight() / 216;
        return (int) ((Gdx.graphics.getHeight()-Gdx.input.getY()) / prop);
    }
}
