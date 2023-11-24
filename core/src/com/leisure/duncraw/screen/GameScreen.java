package com.leisure.duncraw.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.FloorManager;

public class GameScreen extends ScreenAdapter {
  public Color backgroundColor = new Color(4/255f, 4/255f, 4/255f, 1f);
  private final SaveData saveData;
  private final FloorManager floorManager;  
  private final OrthographicCamera camera;
  private final ExtendViewport viewport;
  private final Chara player;

  public GameScreen(SaveData saveData) {
    Logger.log("GameScreen", "Init");
    this.saveData = saveData;
    camera = new OrthographicCamera();
    viewport = null;
    // viewport = new ExtendViewport(camera);
    player = new Chara(null, null);
    floorManager = new FloorManager(saveData, AssetSource.getFloorsData(), saveData.progression.level.level);
  }
  @Override
  public void pause() {
    saveData.save();
  }
  @Override
  public void render(float delta) {
    // Chars, obj, terrain interaction

    ScreenUtils.clear(backgroundColor);
    floorManager.getCurrentFloor().render();
  }
}
