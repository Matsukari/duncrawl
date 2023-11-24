package com.leisure.duncraw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.Screen;
import com.leisure.duncraw.screen.SplashScreen;

public class GameApplication extends Game {	
	@Override
	public void create () {
    Logger.log("GameApplication", "Create");
    AssetSource.init(Gdx.files.local("dungeon_crawler.ini"));
    setScreen(new SplashScreen());
  }	
  @Override
  public void render() {
    if (screen == null) return;
    if (((Screen)screen).next != null) setScreen(((Screen)screen).next);
    screen.render(Gdx.graphics.getDeltaTime());
  }
	@Override
	public void dispose () {
    Logger.log("GameApplication", "Disposed");
	}

}
