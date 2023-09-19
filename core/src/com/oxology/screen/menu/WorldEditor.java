package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.Runagate;
import com.oxology.menu.Button;
import com.oxology.screen.Template;
import com.oxology.world.Level;
import com.oxology.world.Tile;

import java.util.ArrayList;
import java.util.List;

public class WorldEditor extends Template {
    private OrthographicCamera levelCamera;
    private SpriteBatch levelBatch;
    private List<Level> levels;

    private Texture border;

    private Texture cursor;
    private Texture addCursor;
    private int cursorX, cursorY;

    private Button saveBtn;

    public WorldEditor(Runagate game) {
        super(game);

        levelCamera = new OrthographicCamera(384, 216);
        levelCamera.translate(384/2f, 216/2f);
        levelCamera.update();

        levelBatch = new SpriteBatch();

        this.levels = new ArrayList<>();

        this.border = new Texture("level/levelBorder.png");

        this.cursor = new Texture("level/levelBorder2.png");
        this.addCursor = new Texture("level/levelBorderAdd.png");

        this.cursorX = 3;
        this.cursorY = 3;

        BitmapFont font = new BitmapFont(Gdx.files.internal("font/PressStart2P.fnt"));
        font.setColor(Color.WHITE);
        font.getData().scaleX = .09f;
        font.getData().scaleY = .09f;
        this.saveBtn = new Button(332, 189, "Save", font, new Button.Action() {
            @Override
            public void onAction() {
                //saveLevel();
            }
        }, this);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        levelBatch.begin();
        levelBatch.draw(border, 8, 17);

        if(getLevel(cursorX, cursorY) != null)
            levelBatch.draw(cursor, 9+cursorX*48, 18+cursorY*27);
        else
            levelBatch.draw(addCursor, 9+cursorX*48, 18+cursorY*27);

        for(Level level : levels) {
            levelBatch.draw(cursor/*level.getSnippet()*/, 9+level.getX()*48, 18+level.getY()*27, 48, 27);
        }

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
            cursorX = (getX()-9)/48;
            cursorY = (getY()-18)/27;

            if(Gdx.input.isTouched())
                goToLevel();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            goToLevel();
        }

        saveBtn.update();
    }

    public void saveLevel(Level level) {
        for(int i = 0; i < levels.size(); i++) {
            if(level.getX() == levels.get(i).getX() && level.getY() == levels.get(i).getY())
                levels.set(i, level);
        }
    }

    private void goToLevel() {
        Level level = getLevel(cursorX, cursorY);
        if(level == null)
            level = new Level(cursorX, cursorY);

        this.levels.add(level);

        game.setScreen(new LevelEditor(this, game, level));
    }

    public void goToLevel(int x, int y) {
        cursorX = x;
        cursorY = y;

        goToLevel();
    }

    private Level getLevel(int x, int y) {
        for(Level level : levels) {
            if(level.getX() == x && level.getY() == y) return level;
        }

        return null;
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
