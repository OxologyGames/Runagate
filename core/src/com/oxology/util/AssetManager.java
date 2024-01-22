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

    public Texture cursor;
    public Texture worldMesh;
    public Texture levelMesh;
    public Texture levelAdd;
    public Texture levelDelete;

    public Texture playerIdle;
    public Texture playerWalk;
    public Texture playerRun;
    public Texture playerJump;
    public Texture playerLand;

    public AssetManager() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/bahnschrift.ttf"));

        splash = new Texture("menu/splash.png");
        logo = new Texture("menu/main/logo.png");
        pixel = new Texture("menu/pixel.png");
        pixelGray = new Texture("menu/pixelGray.png");

        cursor = new Texture("menu/level/cursor.png");
        worldMesh = new Texture("menu/world/mesh.png");
        levelMesh = new Texture("menu/level/mesh.png");
        levelAdd = new Texture("menu/world/levelAdd.png");
        levelDelete = new Texture("menu/world/levelDelete.png");

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
