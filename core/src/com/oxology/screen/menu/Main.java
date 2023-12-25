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

        menuCamera = new OrthographicCamera(Runagate.MENU_WIDTH, Runagate.MENU_HEIGHT);
        menuCamera.translate(Runagate.MENU_WIDTH/2f, Runagate.MENU_HEIGHT/2f);
        menuCamera.update();

        BitmapFont font = new BitmapFont(Gdx.files.internal("font/PressStart2P.fnt"));
        font.setColor(1, 1, 1, 1);
        font.getData().scaleX = Runagate.MENU_FONT_SCALE;
        font.getData().scaleY = Runagate.MENU_FONT_SCALE;
        playBtn = new Button(142, 112, 1, "Play", font, new Button.Action() {
            @Override
            public void onAction() {
                goToLevelSelector();
            }
        }, this);
        worldBtn = new Button(142, 100, 1, "Edit", font, new Button.Action() {
            @Override
            public void onAction() {
                goToLevelEditor();
            }
        }, this);
        exitBtn = new Button(142, 88, 1, "Exit", font, new Button.Action() {
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
        float prop = (float) Gdx.graphics.getWidth() / Runagate.MENU_WIDTH;
        return (int) (Gdx.input.getX() / prop);
    }

    @Override
    public int getY() {
        float prop = (float) Gdx.graphics.getHeight() / Runagate.MENU_HEIGHT;
        return (int) ((Gdx.graphics.getHeight()-Gdx.input.getY()) / prop);
    }
}
