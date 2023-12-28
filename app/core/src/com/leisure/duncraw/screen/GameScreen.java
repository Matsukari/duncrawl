package com.leisure.duncraw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Enemy;
import com.leisure.duncraw.art.chara.EnemySpawner;
import com.leisure.duncraw.art.chara.Npc;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.ai.AiWanderer;
import com.leisure.duncraw.art.chara.observers.AnimationBehaviour;
import com.leisure.duncraw.art.chara.observers.DashBehaviour;
import com.leisure.duncraw.art.chara.observers.IlluminateBehaviour;
import com.leisure.duncraw.art.chara.observers.TalkBehaviour;
import com.leisure.duncraw.art.chara.observers.dark.InfuseDarknessBehaviour;
import com.leisure.duncraw.art.chara.observers.dark.ShadowCloakBehaviour;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.item.items.StaminaPotion;
import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.art.lighting.PointLight;
import com.leisure.duncraw.art.map.objs.Chest;
import com.leisure.duncraw.art.map.objs.Lamp;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.debug.SpriteBatchDebug;
import com.leisure.duncraw.debug.TerrainSetDebug;
import com.leisure.duncraw.debug.editor.LightEnvEditor;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.CharaManager;
import com.leisure.duncraw.manager.DebugManager;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.manager.FloorManager;
import com.leisure.duncraw.manager.HudManager;
import com.leisure.duncraw.manager.MusicManager;
import com.leisure.duncraw.manager.StoryManager;

import lib.math.Pointi;

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
    viewport = new ExtendViewport(saveData.settings.bounds.width, saveData.settings.bounds.height, camera);
    viewport.apply();
    effectManager = new EffectManager();
    floorManager = new FloorManager(saveData, AssetSource.getFloorsData(), saveData.progression.level.floor, effectManager);
    charaManager = new CharaManager(AssetSource.getCharasData(), floorManager.getCurrentFloor());
    charaManager.observers.add(new AnimationBehaviour(effectManager));
    player = charaManager.add(new Player(CharaData.fromDat(charaManager.sources.player), saveData));
    player.observers.add(new InfuseDarknessBehaviour(effectManager));
    player.observers.add(new ShadowCloakBehaviour(effectManager));
    player.observers.add(new DashBehaviour(effectManager));
    player.observers.add(new IlluminateBehaviour(floorManager.lightEnvironment, new PointLight(Graphics.getSafeTextureRegion("images/lights/light_smooth.png"))));
    musicManager = new MusicManager(this, AssetSource.getMusicData(), saveData.settings.music);
    hudManager = new HudManager(this, AssetSource.getUiData());
    storyManager = new StoryManager(this, saveData.progression.level.scene);

    debugManager = new DebugManager();
    debugManager.debugSystem();
    debugManager.debugPlayer(player);
    debugManager.editFloorGen(floorManager);
    debugManager.debugTool(new SpriteBatchDebug(floorManager.batch));
    debugManager.debugTool(new LightEnvEditor(floorManager.lightEnvironment));
    
    testPlaceScene();
  }
  private void testPlaceScene() {
    Enemy mob = charaManager.addFrom(charaManager.sources.ghost, Enemy.class);
    Chara npc = charaManager.addFrom(charaManager.sources.ghost, Npc.class); 
    Pointi pos = floorManager.getCurrentFloor().getTileInRandomRoom();
    player.setState(new MoveState(pos.x, pos.y, false));
    mob.setState(new MoveState(pos.x + 5, pos.y + 5, false));
    npc.setState(new MoveState(pos.x + 2, pos.y - 1, false));
    // mob.startAI(new AiWanderer(floorManager.getCurrentFloor(), player));
    floorManager.getCurrentFloor().background.putObject(new StaminaPotion("dat/item/stamina_potion.dat"), pos.x - 1, pos.y);
    floorManager.getCurrentFloor().background.putObject(new Chest("dat/obj/chest.dat"), pos.x + 3, pos.y);
    floorManager.getCurrentFloor().background.putObject(new Lamp("dat/obj/lamp.dat", floorManager.lightEnvironment, effectManager), pos.x - 3, pos.y);
    
    // floorManager.getCurrentFloor().initialSpawn(new EnemySpawner(charaManager, charaManager.sources, ()->new AiWanderer(floorManager.getCurrentFloor(), player));
    npc.observers.add(new TalkBehaviour(hudManager.dialogueHud, Conversation.fromDat("dat/convs/test.conv")));
    camera.zoom = 30f;
    debugManager.debugChara(mob);
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

    ScreenUtils.clear(backgroundColor);
    floorManager.renderBackground(camera);
    charaManager.renderAll(camera);
    floorManager.renderForeground(camera);
    effectManager.renderAll(camera);
    hudManager.renderAvailable(camera);
    debugManager.render(camera);
  }
  @Override
  public void dispose() {
    hudManager.dispose();
    charaManager.dispose();
    debugManager.dispose();
    Logger.log("GameScreen", "Dispose");
  } 
  
}
