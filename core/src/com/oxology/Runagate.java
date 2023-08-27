package com.oxology;

import com.badlogic.gdx.Game;
import com.oxology.screen.menu.Main;

public class Runagate extends Game {
	
	@Override
	public void create () {
		this.setScreen(new Main());
	}

	@Override
	public void render () {
		super.render();
	}
}
