package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.oxology.Runagate;
import com.oxology.ui.Button;
import com.oxology.ui.Panel;
import com.oxology.ui.Toggle;
import com.oxology.screen.Template;
import com.oxology.world.Level;
import com.oxology.world.block.BrickBlock;

public class LevelEditor extends Template {
    private Level level;

    private Texture wallTexture;
    private int cursorX, cursorY;

    private Panel panel;
    private Toggle toggle;
    private Button modeBtn;

    private boolean editFreeze;
    private boolean grid;

    private int mode;
    // 0 - Wall
    // 1 - Air
    // 2 - Chain

    private WorldEditor worldEditorScreen;

    public LevelEditor(WorldEditor worldEditorScreen, Level level) {
        super();

        this.worldEditorScreen = worldEditorScreen;

        this.level = level;

        this.wallTexture = new Texture("level/wall.png");

        this.editFreeze = true;
        this.grid = true;

        this.panel = new Panel(0, 0, 330);

        BitmapFont font = Runagate.getInstance().getAssetManager().getBitmapFont(48, 12);
        font.setColor(0, 0, 0, 1);
        Button saveBtn = new Button(40, 1350, 250, 50, "SAVE", font, this::saveLevel);
        Button backBtn = new Button(40, 1290, 250, 50, "BACK", font, this::backToWorld);
        modeBtn = new Button(40, 1230, 250, 50, "WALL", font, this::changeMode);

        this.panel.addElement(saveBtn);
        this.panel.addElement(backBtn);
        this.panel.addElement(modeBtn);

        this.toggle = new Toggle(2560-128, 64, this::togglePanel);

        this.mode = 0;
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        batch.begin();
        for(int i = 0; i < 80; i++) {
            for(int j = 0; j < 45; j++) {
                if(level.getTiles()[i][j] instanceof BrickBlock)
                    batch.draw(wallTexture, i*32, j*32, 32, 32);
            }
        }

        if(grid)
            batch.draw(Runagate.getInstance().getAssetManager().levelMesh, 0, 0, 2560, 1440);

        batch.draw(Runagate.getInstance().getAssetManager().cursor, cursorX*32, cursorY*32, 32, 32);

        panel.draw(batch);

        toggle.draw(batch);
        batch.end();
    }

    public void update(float deltaTime) {
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

        panel.update(deltaTime);

        toggle.update(deltaTime);

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if(!toggle.isHolding() && !editFreeze) {
                switch (mode) {
                    case 0:
                        level.getTiles()[cursorX][cursorY] = new BrickBlock(cursorX, cursorY);
                        break;
//                    case 1:
//                        level.getTiles()[cursorX][cursorY] = Tile.AIR;
//                        break;
//                    case 2:
//                        level.getTiles()[cursorX][cursorY] = Tile.CHAIN;
//                        break;
                }
            }
        } else {
            editFreeze = false;
        }
    }

    private void changeMode() {
        mode++;
        if(mode > 2) mode = 0;

        String text = "WALL";
        switch (mode) {
            case 1:
                text = "AIR";
                break;
            case 2:
                text = "CHAIN";
                break;
        }

        Button button = (Button) panel.getElement(modeBtn);
        button.setText(text);
    }

    private void togglePanel() {
        panel.toggle();
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
