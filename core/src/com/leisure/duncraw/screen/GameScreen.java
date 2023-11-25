package com.leisure.duncraw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.CharaManager;
import com.leisure.duncraw.manager.DebugManager;
import com.leisure.duncraw.manager.FloorManager;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

public class GameScreen extends Screen {
  public Color backgroundColor = new Color(4/255f, 4/255f, 4/255f, 1f);
  private final SaveData saveData;
  private final CharaManager charaManager;
  private final FloorManager floorManager;  
  private final DebugManager debugManager;
  private final OrthographicCamera camera;
  private final ExtendViewport viewport; 
  public GameScreen(SaveData saveData) {
    Logger.log("GameScreen", "Init");
    this.saveData = saveData;
    camera = new OrthographicCamera();
    viewport = new ExtendViewport(saveData.settings.bounds.width, saveData.settings.bounds.height, camera);
    floorManager = new FloorManager(saveData, AssetSource.getFloorsData(), saveData.progression.level.level);
    floorManager.setCurrentFloor(new Floor(TerrainSetGenerator.blank()));
    charaManager = new CharaManager(AssetSource.getCharasData(), floorManager.getCurrentFloor());
    debugManager = new DebugManager();
    debugManager.debugMap(floorManager.getCurrentFloor());
  }
  @Override
  public void pause() {
    Logger.log("GameScreen", "Paused");
    Serializer.save(saveData, Gdx.files.local(AssetSource.instance.save));
  }
  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }
  @Override
  public void render(float delta) {
    camera.update();
    charaManager.updateAll(delta);
    // Chars, obj, terrain interaction

    ScreenUtils.clear(backgroundColor);
    floorManager.getCurrentFloor().render();
    charaManager.renderAll();
    debugManager.render();
  }
  @Override
  public void dispose() {
    Logger.log("GameScreen", "Dispose");
  } 
  
}
