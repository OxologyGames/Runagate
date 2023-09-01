package com.oxology;

import com.oxology.screen.Game;
import com.oxology.screen.menu.Main;

public class Runagate extends com.badlogic.gdx.Game {
	
	@Override
	public void create () {
		this.setScreen(new Main());
	}

	@Override
	public void render () {
		super.render();
	}
}
