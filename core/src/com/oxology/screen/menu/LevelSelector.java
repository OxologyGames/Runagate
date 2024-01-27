package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.oxology.Runagate;
import com.oxology.ui.Button;
import com.oxology.screen.Game;
import com.oxology.screen.Template;
import com.oxology.world.Level;
import com.oxology.world.World;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LevelSelector extends Template {
    private OrthographicCamera camera;
    private List<UUID> worlds;
    private BitmapFont font;
    private Button playBtn;
    private Button backBtn;
    private Button newBtn;
    private int index;
    private File worldsFolder;
    private boolean toPlay;

    public LevelSelector(boolean toPlay) {
        super();

        this.toPlay = toPlay;

        this.camera = new OrthographicCamera(Runagate.MENU_WIDTH, Runagate.MENU_HEIGHT);
        this.camera.translate(Runagate.MENU_WIDTH/2f, Runagate.MENU_HEIGHT/2f);
        this.camera.update();

        this.worlds = new ArrayList<>();
        File gameDataFolder = new File(System.getenv("APPDATA"), "Runagate");
        if(!gameDataFolder.exists()) gameDataFolder.mkdirs();

        worldsFolder = new File(gameDataFolder, "worlds");
        if(!worldsFolder.exists()) gameDataFolder.mkdirs();

        for(File file : worldsFolder.listFiles()) {
            try {
                worlds.add(UUID.fromString(file.getName()));
            } catch(IllegalArgumentException ignored) {}
        }

        font = Runagate.getInstance().getAssetManager().getBitmapFont(48, 12);
        font.setColor(0, 0, 0, 1);
        this.backBtn = new Button(40, 1350, 250, 50, "BACK", font, this::backToMenu);
        this.newBtn = new Button(40, 1290, 250, 50, "NEW", font, this::newWorld);
        this.playBtn = new Button(350, 800, 50, 50, ">", font, this::playWorld);

        this.index = 0;
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        batch.begin();
        for(int i = 0; i < worlds.size(); i++) {
            font.setColor(1, 1, 1, 1);
            font.draw(batch, worlds.get(i).toString(), 460, 1390-i*60);
            font.setColor(0, 0, 0, 1);
        }

        if(index > 0)
            playBtn.draw(batch);

        backBtn.draw(batch);

        if(!toPlay)
            newBtn.draw(batch);
        batch.end();
    }

    public void update(float deltaTime) {
        batch.setProjectionMatrix(this.camera.combined);
        this.camera.update();

        if(getX() > 330) {
            index = Math.min((1410 - (getY()-50))/60, worlds.size());
        }

        playBtn.setY(1410-index*60);

        if(index > 0)
            playBtn.update(deltaTime);

        backBtn.update(deltaTime);

        if(!toPlay)
            newBtn.update(deltaTime);
    }

    private void backToMenu() {
        Runagate.getInstance().setScreen(Runagate.getInstance().getMainMenuScreen());
    }

    private void playWorld() {
        UUID world = worlds.get(index-1);
        List<Level> levels = new ArrayList<>();
        File worldFolder = new File(worldsFolder, world.toString());

        File[] levelFiles = worldFolder.listFiles();
        if(levelFiles != null) {
            for(File levelFile : levelFiles) {
                try {
                    FileInputStream inputStream = new FileInputStream(levelFile);
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                    levels.add((Level) objectInputStream.readObject());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(toPlay)
            Runagate.getInstance().setScreen(new Game(new World(levels, world)));
        else
            Runagate.getInstance().setScreen(new WorldEditor(new World(levels, world)));
    }

    private void newWorld() {
        Runagate.getInstance().setScreen(new WorldEditor());
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
