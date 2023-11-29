package com.leisure.duncraw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.CharaManager;
import com.leisure.duncraw.manager.DebugManager;
import com.leisure.duncraw.manager.FloorManager;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.loader.TmxLoader;

public class GameScreen extends Screen {
  public Color backgroundColor = new Color(4/255f, 4/255f, 4/255f, 1f);
  protected final SaveData saveData;
  protected final CharaManager charaManager;
  protected final FloorManager floorManager;  
  protected final DebugManager debugManager;
  protected final OrthographicCamera camera;
  protected final ExtendViewport viewport; 
  protected Player player;
  public GameScreen(SaveData saveData) {
    Logger.log("GameScreen", "Init");
    this.saveData = saveData;
    camera = new OrthographicCamera();
    viewport = new ExtendViewport(saveData.settings.bounds.width, saveData.settings.bounds.height, camera);
    floorManager = new FloorManager(saveData, AssetSource.getFloorsData(), saveData.progression.level.level);
    floorManager.setCurrentFloor(new Floor(TmxLoader.load(floorManager.sources.startingHall, floorManager.batch, 32, 32)));
    charaManager = new CharaManager(AssetSource.getCharasData(), floorManager.getCurrentFloor());
    player = charaManager.addFrom(charaManager.sources.player, Player.class);
    Chara mob = charaManager.addFrom(charaManager.sources.ghost);
    mob.moveTo(3, 3);

    debugManager = new DebugManager();
    debugManager.debugMap(floorManager.getCurrentFloor());
    debugManager.debugPlayer(player);
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
    floorManager.renderCurrent();
    charaManager.renderAll();
    debugManager.render();
  }
  @Override
  public void dispose() {
    Logger.log("GameScreen", "Dispose");
  } 
  
}
