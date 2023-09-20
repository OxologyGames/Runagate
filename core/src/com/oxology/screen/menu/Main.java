package com.oxology.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oxology.Runagate;
import com.oxology.menu.Button;
import com.oxology.screen.Template;

public class Main extends Template {
    private Button playBtn;
    private Button worldBtn;
    private Button exitBtn;

    private SpriteBatch batch;
    private OrthographicCamera menuCamera;

    private BitmapFont logoFont;
    private float logoWidth;

    public Main(Runagate game) {
        super(game);

        menuCamera = new OrthographicCamera(384, 216);
        menuCamera.translate(384/2f, 216/2f);
        menuCamera.update();

        BitmapFont font = new BitmapFont(Gdx.files.internal("font/PressStart2P.fnt"));
        font.setColor(1, 1, 1, 1);
        font.getData().scaleX = .09f;
        font.getData().scaleY = .09f;
        playBtn = new Button(167, 112, "Play", font, new Button.Action() {
            @Override
            public void onAction() {
                goToLevelSelector();
            }
        }, this);
        worldBtn = new Button(167, 100, "Edit", font, new Button.Action() {
            @Override
            public void onAction() {
                goToLevelEditor();
            }
        }, this);
        exitBtn = new Button(167, 88, "Exit", font, new Button.Action() {
            @Override
            public void onAction() {
                Gdx.app.exit();
            }
        }, this);

        batch = new SpriteBatch();

        logoFont = new BitmapFont(Gdx.files.internal("font/PressStart2P.fnt"));
        logoFont.getData().scaleX = .4f;
        logoFont.getData().scaleY = .4f;
        GlyphLayout layout = new GlyphLayout(logoFont, "RUNAGATE");
        this.logoWidth = layout.width;
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        logoFont.draw(batch, "RUNAGATE", 384/2f - logoWidth/2f, 180);
        playBtn.draw(batch);
        worldBtn.draw(batch);
        exitBtn.draw(batch);
        batch.end();
    }

    private void update(float deltaTime) {
        menuCamera.update();
        batch.setProjectionMatrix(menuCamera.combined);

        playBtn.update();
        worldBtn.update();
        exitBtn.update();
    }

    public void goToLevelSelector() {
        game.setScreen(new LevelSelector(game));
    }

    public void goToLevelEditor() {
        game.setScreen(new WorldEditor(game));
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
