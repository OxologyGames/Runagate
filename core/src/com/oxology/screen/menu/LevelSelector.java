package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.oxology.Runagate;
import com.oxology.menu.Button;
import com.oxology.screen.Template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LevelSelector extends Template {
    private OrthographicCamera camera;
    private List<UUID> worlds;
    private BitmapFont font;
    private Button playBtn;
    private int index;

    public LevelSelector(Runagate game) {
        super(game);

        this.camera = new OrthographicCamera(384, 216);
        this.camera.translate(384/2f, 216/2f);
        this.camera.update();

        this.worlds = new ArrayList<>();
        File gameDataFolder = new File(System.getenv("APPDATA"), "Runagate");
        if(!gameDataFolder.exists()) gameDataFolder.mkdirs();

        File worldsFolder = new File(gameDataFolder, "worlds");
        if(!worldsFolder.exists()) gameDataFolder.mkdirs();

        for(File file : worldsFolder.listFiles()) {
            worlds.add(UUID.fromString(file.getName()));
        }

        font = new BitmapFont(Gdx.files.internal("font/PressStart2P.fnt"));
        font.setColor(1, 1, 1, 1);
        font.getData().scaleX = .09f;
        font.getData().scaleY = .09f;

        playBtn = new Button(16, 195, 2, "", font, new Button.Action() {
            @Override
            public void onAction() {
                playWorld();
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

        playBtn.draw(batch);
        batch.end();
    }

    private void update(float deltaTime) {
        batch.setProjectionMatrix(this.camera.combined);
        this.camera.update();

        if(216-getY() > 15 && 216-getY() < 216) {
            index = Math.min((217-getY())/12, worlds.size());
        }

        playBtn.setY(217-(10+index*12));
        playBtn.update();
    }

    private void playWorld() {
        UUID world = worlds.get(index-1);
        System.out.println(world);
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
