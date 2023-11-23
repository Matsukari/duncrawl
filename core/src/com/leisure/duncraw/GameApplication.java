package com.leisure.duncraw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leisure.duncraw.manager.CharaManager;
import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.screen.MenuScreen;
import com.leisure.duncraw.screen.ScreenChanger;
import com.leisure.duncraw.screen.SplashScreen;

public class GameApplication extends Game {	
  private Audio audio;
  private Graphics graphics;
  private AssetManager assets;
  public class Screener implements ScreenChanger {
    Game game;
    public Screener(Game game) { this.game = game; }
    @Override
    public void change(Screen screen) {
      game.setScreen(screen);
    }
  }
	@Override
	public void create () {
    setScreen(new SplashScreen(new Screener(this)));
  }	
	@Override
	public void dispose () {
	}

}
