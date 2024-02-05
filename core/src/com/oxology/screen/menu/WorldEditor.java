package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.oxology.Runagate;
import com.oxology.ui.Button;
import com.oxology.screen.Template;
import com.oxology.world.Level;
import com.oxology.world.World;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class WorldEditor extends Template {
    private static final int ADD_MODE = 0;
    private static final int DELETE_MODE = 1;

    private World world;

    private int cursorX, cursorY;

    private int mode;

    private final Button saveBtn;
    private final Button backBtn;
    private final Button modeBtn;

    public WorldEditor() {
        super();

        world = new World(new ArrayList<>(), UUID.randomUUID());

        this.cursorX = 0;
        this.cursorY = 0;

        BitmapFont font = Runagate.getInstance().getAssetManager().getBitmapFont(48, 12);
        font.setColor(0, 0, 0, 1);
        this.saveBtn = new Button(40, 1350, 250, 50, "SAVE", font, this::saveWorld);
        this.backBtn = new Button(40, 1290, 250, 50, "BACK", font, this::goBack);
        this.modeBtn = new Button(40, 1230, 250, 50, mode == ADD_MODE ? "ADD" : "DELETE", font, this::changeMode);
    }

    public WorldEditor(World world) {
        this();
        this.world = world;
        for(Level level : world.getLevels()) {
            level.generateSnippet();
        }
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        batch.begin();
        batch.draw(Runagate.getInstance().getAssetManager().pixelGray, 330, 40, 2190, 1360);
        batch.draw(Runagate.getInstance().getAssetManager().worldMesh, 330, 40, 2190, 1360);
        if(getLevel(cursorX, cursorY) == null && mode == 0)
            batch.draw(Runagate.getInstance().getAssetManager().levelAdd, ((2520-330)/2f)+138+cursorX*384, ((1360-40)/2f)-26+cursorY*216);
        else if(getLevel(cursorX, cursorY) != null && mode == 1)
            batch.draw(Runagate.getInstance().getAssetManager().levelDelete, ((2520-330)/2f)+138+cursorX*384, ((1360-40)/2f)-26+cursorY*216);

        for(Level level : world.getLevels()) {
            batch.draw(level.getSnippet(), ((2520-330)/2f)+138+level.getX()*384, ((1360-40)/2f)-26+level.getY()*216, 384, 216, 0, 0, 1, 1);
        }
        saveBtn.draw(batch);
        backBtn.draw(batch);
        modeBtn.draw(batch);
        batch.end();
    }

    public void update(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        if(getX() > 330 && getX() < 2520 && getY() > 40 && getY() < 1400) {
            int mouseX = (getX()-(2520-330)/2)-138;
            cursorX = (mouseX < 0 ? mouseX-384 : mouseX)/384;
            int mouseY = (getY()-(1360-40)/2)+26;
            cursorY = (mouseY < 0 ? mouseY-216 : mouseY)/216;

            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
                goToLevel();
        }

        saveBtn.update(deltaTime);
        backBtn.update(deltaTime);
        modeBtn.update(deltaTime);
    }

    public void saveLevel(Level level) {
        for(int i = 0; i < world.getLevels().size(); i++) {
            if(level.getX() == world.getLevels().get(i).getX() && level.getY() == world.getLevels().get(i).getY())
                world.getLevels().set(i, level);
        }
    }

    private void goBack() {
        Runagate.getInstance().setScreen(Runagate.getInstance().getMainMenuScreen());
    }

    private void saveWorld() {
        File gameDataFolder = new File(System.getenv("APPDATA"), "Runagate");
        if(!gameDataFolder.exists()) gameDataFolder.mkdirs();

        File worldsFolder = new File(gameDataFolder, "worlds");
        if(!worldsFolder.exists()) gameDataFolder.mkdirs();

        File world = new File(worldsFolder, this.world.getId().toString());
        if(!world.exists()) world.mkdirs();

        for(Level level : this.world.getLevels()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(world + File.separator + level.getX() + "_" + level.getY() + ".dat");
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
                outputStream.writeObject(level);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void goToLevel() {
        Level level = getLevel(cursorX, cursorY);
        if(level == null) {
            level = new Level(cursorX, cursorY);
            this.world.addLevel(level);
        }

        Runagate.getInstance().setScreen(new LevelEditor(this, level));
    }

    public void goToLevel(int x, int y) {
        cursorX = x;
        cursorY = y;

        goToLevel();
    }

    private Level getLevel(int x, int y) {
        for(Level level : world.getLevels()) {
            if(level.getX() == x && level.getY() == y) return level;
        }

        return null;
    }

    private void changeMode() {
        mode = mode == ADD_MODE ? DELETE_MODE : ADD_MODE;
        modeBtn.setText(mode == ADD_MODE ? "ADD" : "DELETE");
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
