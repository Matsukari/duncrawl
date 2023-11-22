package com.leisure.duncraw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameApplication extends Game {	
	@Override
	public void create () { 
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
