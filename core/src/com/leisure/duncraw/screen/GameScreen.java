package com.leisure.duncraw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Enemy;
import com.leisure.duncraw.art.chara.Npc;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.observers.AnimationBehaviour;
import com.leisure.duncraw.art.chara.observers.DashBehaviour;
import com.leisure.duncraw.art.chara.observers.TalkBehaviour;
import com.leisure.duncraw.art.chara.observers.dark.InfuseDarknessBehaviour;
import com.leisure.duncraw.art.chara.observers.dark.ShadowCloakBehaviour;
import com.leisure.duncraw.art.item.items.HealthPotion;
import com.leisure.duncraw.art.item.items.StaminaPotion;
import com.leisure.duncraw.art.map.objs.Chest;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.CharaManager;
import com.leisure.duncraw.manager.DebugManager;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.manager.FloorManager;
import com.leisure.duncraw.manager.HudManager;
import com.leisure.duncraw.manager.MusicManager;
import com.leisure.duncraw.manager.StoryManager;

public class GameScreen extends Screen {
  protected Color backgroundColor = new Color(4/255f, 4/255f, 4/255f, 1f);
  protected final SaveData saveData;
  protected final DebugManager debugManager;
  protected final StoryManager storyManager;
  protected final MusicManager musicManager;
  public final HudManager hudManager;
  public final CharaManager charaManager;
  public final FloorManager floorManager;  
  public final EffectManager effectManager;
  public final OrthographicCamera camera;
  public final ExtendViewport viewport; 
  public Player player;
  public boolean blockCamera = false; 
  public GameScreen(SaveData saveData) {
    Logger.log("GameScreen", "Init");
    this.saveData = saveData;
    camera = new OrthographicCamera();
    effectManager = new EffectManager();
    viewport = new ExtendViewport(saveData.settings.bounds.width, saveData.settings.bounds.height, camera);
    hudManager = new HudManager(viewport);
    floorManager = new FloorManager(saveData, AssetSource.getFloorsData(), saveData.progression.level.floor);
    storyManager = new StoryManager(this, saveData.progression.level.scene);
    musicManager = new MusicManager(this, AssetSource.getMusicData());
    // floorManager.setCurrentFloor(TmxLoader.load(floorManager.sources.startingHall, floorManager.batch, 32, 32));
    charaManager = new CharaManager(AssetSource.getCharasData(), floorManager.getCurrentFloor());
    charaManager.observers.add(new AnimationBehaviour(effectManager));
    player = charaManager.addFrom(charaManager.sources.player, Player.class);
    player.observers.add(new InfuseDarknessBehaviour(effectManager));
    player.observers.add(new ShadowCloakBehaviour(effectManager));
    player.observers.add(new DashBehaviour());
    Chara mob = charaManager.addFrom(charaManager.sources.ghost, Enemy.class);
    Chara npc = charaManager.addFrom(charaManager.sources.ghost, Npc.class);
    

    Vector2 pos = floorManager.getCurrentFloor().getTileInRandomRoom();
    player.moveTo(pos.x, pos.y);
    mob.moveTo(pos.x + 5, pos.y + 5);
    npc.moveTo(pos.x + 2, pos.y - 1);
    floorManager.getCurrentFloor().terrainSet.putObject(new StaminaPotion(floorManager.batch, "dat/item/stamina_potion.dat"), (int)pos.x - 1, (int)pos.y);
    npc.observers.add(new TalkBehaviour(hudManager.dialogueHud, Conversation.fromDat("dat/convs/test.conv")));
    camera.zoom = 30f;
    floorManager.getCurrentFloor().terrainSet.putObject(new Chest("dat/obj/chest.dat", floorManager.batch), (int)pos.x + 3, (int)pos.y);
    Logger.log("Playerpos", player.bounds.toString());

    // storyManager.play();

    debugManager = new DebugManager();
    debugManager.debugSystem();
    debugManager.debugPlayer(player);
    debugManager.debugChara(mob);
    debugManager.editFloorGen(floorManager);
    // debugManager.debugMap(floorManager.getCurrentFloor());
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
    if (!blockCamera) {
      camera.position.x = player.bounds.x;
      camera.position.y = player.bounds.y;
    }
    camera.update();
    charaManager.updateAll(delta);
    effectManager.updateAll(delta);
    storyManager.updateScene();
    hudManager.update(delta);
    musicManager.update();
    // Chars, obj, terrain interaction

    ScreenUtils.clear(backgroundColor);
    floorManager.renderBackground(camera);
    charaManager.renderAll(camera);
    effectManager.renderAll(camera);
    floorManager.renderForeground(camera);
    hudManager.renderAvailable(camera);
    debugManager.render(camera);
  }
  @Override
  public void dispose() {
    hudManager.dispose();
    Logger.log("GameScreen", "Dispose");
  } 
  
}
