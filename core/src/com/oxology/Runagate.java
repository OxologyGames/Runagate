package com.oxology;

import com.badlogic.gdx.utils.ScreenUtils;
import com.oxology.screen.Splash;

import java.util.Map;

public class Runagate extends com.badlogic.gdx.Game {
	private int resX, resY;

	public Runagate(Map<String, Object> settings) {
		this.resX = (int) settings.get("width");
		this.resY = (int) settings.get("height");
	}

	@Override
	public void create () {
		this.setScreen(new Splash(this));
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
}
