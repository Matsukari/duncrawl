package com.leisure.duncraw.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.logging.Logger;

public class MenuScreen extends Screen {
  protected SaveData save;
  public MenuScreen() {
  }
  @Override
  public Screen next() {
    save = AssetSource.getSaveData();
    return new GameScreen(save);
  }
  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.BLACK);
  }
}
