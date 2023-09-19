package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.Runagate;
import com.oxology.menu.Button;
import com.oxology.screen.Template;
import com.oxology.world.GameObject;
import com.oxology.world.Level;
import com.oxology.world.Tile;

public class LevelEditor extends Template {
    private OrthographicCamera levelCamera;
    private SpriteBatch levelBatch;
    private Level level;

    private Texture border;

    private Texture airTexture, wallTexture;
    private Texture cursor;
    private Texture chainTexture;
    private int cursorX, cursorY;

    private Button saveBtn;
    private Button backBtn;
    private Button modeBtn;

    private int mode;
    // 0 - Wall
    // 1 - Air
    // 2 - Chain

    private WorldEditor worldEditorScreen;

    public LevelEditor(WorldEditor worldEditorScreen, Runagate game, Level level) {
        super(game);

        this.worldEditorScreen = worldEditorScreen;

        levelCamera = new OrthographicCamera(384, 216);
        levelCamera.translate(384/2f, 216/2f);
        levelCamera.update();

        levelBatch = new SpriteBatch();

        this.level = level;

        this.border = new Texture("level/levelBorder.png");

        this.airTexture = new Texture("level/air.png");
        this.wallTexture = new Texture("level/wall.png");
        this.chainTexture = new Texture("level/chain.png");

        this.cursor = new Texture("level/cursor.png");

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
        this.backBtn = new Button(332, 177, "Back", font, new Button.Action() {
            @Override
            public void onAction() {
                backToWorld();
            }
        }, this);
        this.modeBtn = new Button(332, 165, "Wall", font, new Button.Action() {
            @Override
            public void onAction() {
                changeMode();
            }
        }, this);

        this.mode = 0;
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        levelBatch.begin();
        levelBatch.draw(border, 8, 17);

        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 30; j++) {
                Texture texture = airTexture;
                if(level.getTiles()[i][j] == Tile.WALL) texture = wallTexture;
                levelBatch.draw(texture, 9+i*8, 18+j*6);
            }
        }

        levelBatch.draw(cursor, 9+cursorX*8, 18+cursorY*6);
        saveBtn.draw(levelBatch);
        backBtn.draw(levelBatch);
        modeBtn.draw(levelBatch);

        for(GameObject gameObject : level.getGameObjects()) {
            levelBatch.draw(chainTexture, 9+gameObject.getX()*8, 18+gameObject.getY()*6);
        }

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

        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                saveLevel();
                worldEditorScreen.goToLevel(level.getX(), level.getY() + 1);
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                saveLevel();
                worldEditorScreen.goToLevel(level.getX(), level.getY() - 1);
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                saveLevel();
                worldEditorScreen.goToLevel(level.getX() + 1, level.getY());
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                saveLevel();
                worldEditorScreen.goToLevel(level.getX() - 1, level.getY());
            }
        }

        if(getX() > 8 && getX() < 328 && getY() > 17 && getY() < 197) {
            cursorX = (getX()-9)/8;
            cursorY = (getY()-18)/6;

            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                switch (mode) {
                    case 0:
                        level.getTiles()[cursorX][cursorY] = Tile.WALL;
                        break;
                    case 1:
                        level.getTiles()[cursorX][cursorY] = Tile.AIR;
                        break;
                }
            }

            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && mode == 2) {
                int chainIndex = -1;
                for(int i = 0; i < level.getGameObjects().size(); i++) {
                    if(level.getGameObjects().get(i).getX() == cursorX && level.getGameObjects().get(i).getY() == cursorY)
                        chainIndex = i;
                }

                if(chainIndex != -1) {
                    level.getGameObjects().remove(chainIndex);
                } else {
                    level.getGameObjects().add(new GameObject(cursorX, cursorY, GameObject.ObjectType.CHAIN));
                }
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(level.getTiles()[cursorX][cursorY] == Tile.AIR)
                level.getTiles()[cursorX][cursorY] = Tile.WALL;
            else
                level.getTiles()[cursorX][cursorY] = Tile.AIR;
        }

        saveBtn.update();
        backBtn.update();
        modeBtn.update();
    }

    private void changeMode() {
        mode++;
        if(mode > 2) mode = 0;

        String text = "Wall";
        switch (mode) {
            case 1:
                text = "Air";
                break;
            case 2:
                text = "Chain";
                break;
        }

        modeBtn.setText(text);
        GlyphLayout layout = new GlyphLayout(modeBtn.getFont(), modeBtn.getText());
        modeBtn.setWidth(layout.width);
    }

    private void saveLevel() {
        level.generateSnippet();
        worldEditorScreen.saveLevel(level);
    }

    private void backToWorld() {
        saveLevel();
        game.setScreen(worldEditorScreen);
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
