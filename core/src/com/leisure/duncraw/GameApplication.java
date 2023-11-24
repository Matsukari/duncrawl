package com.leisure.duncraw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.screen.ScreenChanger;
import com.leisure.duncraw.screen.SplashScreen;

public class GameApplication extends Game {	
  public class Screener implements ScreenChanger {
    private final Game game;
    public Screener(Game game) { this.game = game; }
    @Override
    public void change(Screen screen) {
      game.setScreen(screen);
    }
  }
	@Override
	public void create () {
    AssetSource.init(Gdx.files.local("dungeon_crawler.ini"));
    setScreen(new SplashScreen(new Screener(this)));
  }	
	@Override
	public void dispose () {
	}

}
