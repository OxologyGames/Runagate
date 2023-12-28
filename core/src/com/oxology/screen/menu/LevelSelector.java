package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.oxology.Runagate;
import com.oxology.menu.Button;
import com.oxology.screen.Game;
import com.oxology.screen.Template;
import com.oxology.world.Level;

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
    private int index;
    private File worldsFolder;

    public LevelSelector() {
        super();

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

        font = new BitmapFont(Gdx.files.internal("font/PressStart2P.fnt"));
        font.setColor(1, 1, 1, 1);
        font.getData().scaleX = Runagate.MENU_FONT_SCALE;
        font.getData().scaleY = Runagate.MENU_FONT_SCALE;

        this.playBtn = new Button(16, 195, 2, "", font, new Button.Action() {
            @Override
            public void onAction() {
                playWorld();
            }
        }, this);
        this.backBtn = new Button(332, 189, 0, "Back", font, new Button.Action() {
            @Override
            public void onAction() {
                backToMenu();
            }
        }, this);

        this.index = 0;
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        batch.begin();
        for(int i = 0; i < worlds.size(); i++) {
            font.draw(batch, worlds.get(i).toString(), 36, 194-i*12);
        }

        if(index > 0)
            playBtn.draw(batch);

        backBtn.draw(batch);
        batch.end();
    }

    public void update(float deltaTime) {
        batch.setProjectionMatrix(this.camera.combined);
        this.camera.update();

        if(216-getY() > 15 && 216-getY() < 216) {
            index = Math.min((217-getY())/12, worlds.size());
        }

        playBtn.setY(217-(10+index*12));

        if(index > 0)
            playBtn.update();

        backBtn.update();
    }

    private void backToMenu() {
        Runagate.getInstance().setScreen(Runagate.getInstance().getMainMenuScreen());
    }

    private void playWorld() {
        UUID world = worlds.get(index-1);

        List<Level> levels = new ArrayList<>();
        File worldFolder = new File(worldsFolder, world.toString());
        for(File levelFile : worldFolder.listFiles()) {
            try {
                FileInputStream inputStream = new FileInputStream(levelFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                levels.add((Level) objectInputStream.readObject());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        Runagate.getInstance().setScreen(new Game(levels));
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
