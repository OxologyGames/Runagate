package com.oxology;

import com.badlogic.gdx.utils.ScreenUtils;
import com.oxology.screen.Splash;
import com.oxology.screen.menu.Main;
import com.oxology.util.TextureManager;

import java.util.Map;

public class Runagate extends com.badlogic.gdx.Game {
	private static Runagate instance;

	public static final int MENU_WIDTH = 1920;
	public static final int MENU_HEIGHT = 1080;
	public static final float MENU_FONT_SCALE = .09f;

	private int resX, resY;

	private TextureManager textureManager;

	private Main mainMenuScreen;

	public Runagate(Map<String, Object> settings) {
		this.resX = (int) settings.get("width");
		this.resY = (int) settings.get("height");
	}

	@Override
	public void create () {
		this.mainMenuScreen = new Main();
		this.setScreen(new Splash());
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		super.render();
	}

	public int getResX() {
		return resX;
	}

	public int getResY() {
		return resY;
	}

	public Main getMainMenuScreen() {
		return mainMenuScreen;
	}

	public static void setInstance(Runagate runagate) {
		instance = runagate;
	}

	public static Runagate getInstance() {
		return instance;
	}

	public TextureManager getTextureManager() {
		if(textureManager == null)
			textureManager = new TextureManager();

		return textureManager;
	}
}
