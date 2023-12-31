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

    public AssetManager() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/bahnschrift.ttf"));

        splash = new Texture("splash.png");
        logo = new Texture("logo.png");
        pixel = new Texture("pixel.png");
    }

    public BitmapFont getBitmapFont(int size, int spacing) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.spaceX = spacing;
        return generator.generateFont(parameter);
    }
}
