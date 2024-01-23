package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxology.Runagate;
import com.oxology.ui.Button;
import com.oxology.ui.Toggle;
import com.oxology.screen.Template;
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

    private Toggle toggle;

    private boolean editable;
    private boolean grid;

    private int mode;
    // 0 - Wall
    // 1 - Air
    // 2 - Chain

    private WorldEditor worldEditorScreen;

    public LevelEditor(WorldEditor worldEditorScreen, Level level) {
        super();

        this.worldEditorScreen = worldEditorScreen;

        levelCamera = new OrthographicCamera(Runagate.MENU_WIDTH, Runagate.MENU_HEIGHT);
        levelCamera.translate(Runagate.MENU_WIDTH/2f, Runagate.MENU_HEIGHT/2f);
        levelCamera.update();

        levelBatch = new SpriteBatch();

        this.level = level;

        this.border = new Texture("level/levelBorder.png");

        this.airTexture = new Texture("level/air.png");
        this.wallTexture = new Texture("level/wall.png");
        this.chainTexture = new Texture("level/chain.png");

        this.editable = false;
        this.grid = true;

        this.cursor = new Texture("level/cursor.png");

        BitmapFont font = Runagate.getInstance().getAssetManager().getBitmapFont(48, 12);
        font.setColor(0, 0, 0, 1);
        this.saveBtn = new Button(40, 1350, 250, 50, "Save", font, this::saveLevel);
        this.backBtn = new Button(40, 1290, 250, 50, "Back", font, this::backToWorld);
        this.modeBtn = new Button(40, 1230, 250, 50, "Wall", font, this::changeMode);

        this.toggle = new Toggle(250, 860);

        this.mode = 0;
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        batch.begin();
        for(int i = 0; i < 80; i++) {
            for(int j = 0; j < 45; j++) {
                if(level.getTiles()[i][j] == Tile.WALL)
                    batch.draw(wallTexture, i*32, j*32, 32, 32);
            }
        }

        if(grid)
            batch.draw(Runagate.getInstance().getAssetManager().levelMesh, 0, 0, 2560, 1440);

        batch.draw(Runagate.getInstance().getAssetManager().cursor, cursorX*32, cursorY*32, 32, 32);

        saveBtn.draw(batch);
        backBtn.draw(batch);
        modeBtn.draw(batch);

        toggle.draw(batch);
        batch.end();
    }

    public void update(float deltaTime) {
        levelCamera.update();
        levelBatch.setProjectionMatrix(levelCamera.combined);

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

        if(Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            grid = !grid;
        }

        cursorX = Math.min(getX()/32, 79);
        cursorY = Math.min(getY()/32, 44);

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            switch (mode) {
                case 0:
                    level.getTiles()[cursorX][cursorY] = Tile.WALL;
                    break;
                case 1:
                    level.getTiles()[cursorX][cursorY] = Tile.AIR;
                    break;
                case 2:
                    level.getTiles()[cursorX][cursorY] = Tile.CHAIN;
                    break;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(level.getTiles()[cursorX][cursorY] == Tile.AIR)
                level.getTiles()[cursorX][cursorY] = Tile.WALL;
            else
                level.getTiles()[cursorX][cursorY] = Tile.AIR;
        }

        saveBtn.update(deltaTime);
        backBtn.update(deltaTime);
        modeBtn.update(deltaTime);

        toggle.update(deltaTime);
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
        Runagate.getInstance().setScreen(worldEditorScreen);
    }

    @Override
    public int getX() {
        float prop = (float) Gdx.graphics.getWidth() / Runagate.MENU_WIDTH;
        return (int) (Gdx.input.getX() / prop);
    }

    @Override
    public int getY() {
        float prop = (float) Gdx.graphics.getHeight() / Runagate.MENU_HEIGHT;
        return (int) ((Gdx.graphics.getHeight()-Gdx.input.getY()) / prop);
    }
}
