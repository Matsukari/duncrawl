package com.leisure.duncraw.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.logging.Logger;

public class MenuScreen extends Screen {
  private SaveData save;
  public MenuScreen() {
    Logger.log("MenuScreen", "Init");
    save = AssetSource.getSaveData();
    next = new GameScreen(save);
  }
  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.BLACK);
  }
}
