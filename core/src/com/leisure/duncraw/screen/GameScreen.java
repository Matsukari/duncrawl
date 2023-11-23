package com.leisure.duncraw.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leisure.duncraw.manager.TilemapManager;
import com.leisure.duncraw.map.Tilemap;

public class GameScreen extends ScreenAdapter {
  public Color backgroundColor = new Color(4/255f, 4/255f, 4/255f, 1f);
  private TilemapManager floorManager;  

  public GameScreen() {}
  @Override
  public void render(float delta) {
    // Chars, obj, terrain interaction

    ScreenUtils.clear(backgroundColor);
    floorManager.getCurrentFloor().render();
  }
}
