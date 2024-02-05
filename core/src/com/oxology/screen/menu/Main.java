package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.oxology.Runagate;
import com.oxology.ui.Button;
import com.oxology.screen.Template;

public class Main extends Template {
    private final Button playBtn;
    private final Button worldBtn;
    private final Button exitBtn;

    private final BitmapFont version;

    public Main() {
        super(true, true);

        version = Runagate.getInstance().getAssetManager().getBitmapFont(24, 6);
        version.setColor(1, 1, 1, 1);

        BitmapFont font = Runagate.getInstance().getAssetManager().getBitmapFont(64, 16);
        font.setColor(0, 0, 0, 1);
        playBtn = new Button(Runagate.MENU_WIDTH/2f - 720/2f, 700, 720, 128, "PLAY", font, this::goToLevelSelector);
        worldBtn = new Button(Runagate.MENU_WIDTH/2f - 720/2f, 550, 720, 128, "EDIT", font, this::goToLevelSelectorToEdit);
        exitBtn = new Button(Runagate.MENU_WIDTH/2f - 720/2f, 400, 720, 128, "EXIT", font, Gdx.app::exit);

        setFading(true);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        batch.begin();
        batch.draw(Runagate.getInstance().getAssetManager().logo, (Runagate.MENU_WIDTH/2f)-(1500/2f), 925);

        playBtn.draw(batch);
        exitBtn.draw(batch);
        worldBtn.draw(batch);

        version.draw(batch, "Version: " + Runagate.VERSION, 5, 25);
        batch.end();
    }

    public void update(float delta) {
        super.update(delta);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        playBtn.update(delta);
        worldBtn.update(delta);
        exitBtn.update(delta);

        version.setColor(1, 1, 1, batch.getColor().a);
    }

    public void goToLevelSelectorToEdit() {
        Runagate.getInstance().setScreen(new LevelSelector(false));
    }

    public void goToLevelSelector() {
        Runagate.getInstance().setScreen(new LevelSelector(true));
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
