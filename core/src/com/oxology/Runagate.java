package com.oxology;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oxology.screen.Splash;
import com.oxology.screen.Template;
import com.oxology.screen.menu.Main;
import com.oxology.util.AssetManager;

import java.util.Map;

public class Runagate extends com.badlogic.gdx.Game {
	public static final String VERSION = "1.0alpha";

	private static Runagate instance;

	public static final int MENU_WIDTH = 2560;
	public static final int MENU_HEIGHT = 1440;
	public static final float MENU_FONT_SCALE = 0.2f;

	private int resX, resY;

	private AssetManager assetManager;

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

	public AssetManager getAssetManager() {
		if(assetManager == null)
			assetManager = new AssetManager();

		return assetManager;
	}

	public int getX() {
		if(getScreen() != null && getScreen() instanceof Template) {
			return ((Template) getScreen()).getX();
		}

		return Gdx.input.getX();
	}

	public int getY() {
		if(getScreen() != null && getScreen() instanceof Template) {
			return ((Template) getScreen()).getY();
		}

		return Gdx.input.getY();
	}
}
