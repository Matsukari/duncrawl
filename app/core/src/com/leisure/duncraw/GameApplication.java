package com.leisure.duncraw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.helper.Instantiator;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.Screen;

public class GameApplication extends Game {
  private Instantiator<Screen> nextScreen;
  public GameApplication(Instantiator<Screen> nextScreen) {
    this.nextScreen = nextScreen;
  }
	@Override
	public void create () {
    Logger.log("GameApplication", "Create");
    AssetSource.init(Gdx.files.local("dungeon_crawler.ini"));
    Graphics.init();
    Audio.init();
    setScreen(nextScreen.instance());
  }	
  @Override
  public void render() {
    if (screen == null) return;
    if (((Screen)screen).hasChanged) {
      screen.pause();
      screen.dispose();
      setScreen(((Screen)screen).next());
    }
    screen.render(Gdx.graphics.getDeltaTime());
  }
	@Override
	public void dispose () {
    if (screen != null) screen.dispose();
    Graphics.dispose(); 
    Audio.dispose();
    Logger.log("GameApplication", "Disposed");
	}

}
