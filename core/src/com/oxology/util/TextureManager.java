package com.oxology.util;

import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
    public Texture splash;
    public Texture logo;

    public TextureManager() {
        splash = new Texture("splash.png");
        logo = new Texture("logo.png");
    }
}
