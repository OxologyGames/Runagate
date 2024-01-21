package com.oxology.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class AssetManager {
    private final FreeTypeFontGenerator generator;

    public Texture splash;
    public Texture logo;
    public Texture pixel;
    public Texture pixelGray;
    public Texture worldMesh;

    public Texture playerIdle;
    public Texture playerWalk;
    public Texture playerRun;
    public Texture playerJump;
    public Texture playerLand;

    public AssetManager() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/bahnschrift.ttf"));

        splash = new Texture("splash.png");
        logo = new Texture("logo.png");
        pixel = new Texture("pixel.png");
        pixelGray = new Texture("pixelGray.png");
        worldMesh = new Texture("worldMesh.png");

        playerIdle = new Texture("entity/player/idle.png");
        playerWalk = new Texture("entity/player/walk.png");
        playerRun = new Texture("entity/player/run.png");
        playerJump = new Texture("entity/player/jump.png");
        playerLand = new Texture("entity/player/land.png");
    }

    public BitmapFont getBitmapFont(int size, int spacing) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.spaceX = spacing;
        return generator.generateFont(parameter);
    }
}
