package com.leisure.duncraw.manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.EntityArrayList;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.observers.AttackBehaviour;
import com.leisure.duncraw.art.chara.observers.HurtBehaviour;
import com.leisure.duncraw.art.chara.observers.SoundBehaviour;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.CharasData;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;

public class CharaManager {
  // Contains all charas that are alive
  public EntityArrayList<Chara> charas;
  // Queue for killing (When it needs to animate before completely removing)
  public Queue<Chara> deadCharas = new LinkedList<>(); 
  public SpriteBatch batch = new SpriteBatch();
  public Chara player;
  private final FloorManager floorManager;
  public final CharasData sources;
  public final ArrayList<Observer> observers = new ArrayList<>();
  public CharaManager(CharasData sources, FloorManager floorManager, RenderSortManager renderManager) { 
    this.sources = sources;
    this.floorManager = floorManager;
    charas = new EntityArrayList<Chara>(renderManager);
    observers.add(new AttackBehaviour());
    observers.add(new HurtBehaviour());
    observers.add(new SoundBehaviour());
  }

  public <T extends Chara> T addFrom(String source, Class<T> clazz) {
    Logger.log("CharaManager", "Creating a character from source: " + source);
    CharaData data = Deserializer.safeLoad(CharaData.class, source);
    try { 
      return add(clazz.getDeclaredConstructor(CharaData.class).newInstance(data));
    } catch (Exception e) { e.printStackTrace(); System.exit(-1); }
    return null;
  }
  public Chara addFrom(String source) { return addFrom(source, Chara.class); }
  public <T extends Chara> T add(T chara) { 
    chara.bounds.setSize(32, 32);
    chara.mapAgent = new TilemapChara(chara, floorManager);
    floorManager.getFloor().putChara(chara.mapAgent); 
    charas.add(chara);
    for (Observer observer : observers) chara.observers.add(observer.copy());
    return chara;  
  }
  public void kill(int id) {}
  public void updateAll(float dt) {
    for (Chara chara : charas.data) {
      chara.update(dt);
      if (chara.status.health <= 0) {
        deadCharas.add(chara);
        chara.status.dead = true;
      }
    }
    for (Chara deadChara = deadCharas.poll(); deadChara != null; deadChara = deadCharas.poll()) {
      Logger.log("CharaManager", String.format("Killed %d", deadChara.id));
      deadChara.onDeath();
      charas.remove(deadChara);
    }
  }
  public void renderAll(Camera cam) {
    batch.setProjectionMatrix(cam.combined);
    batch.begin();
    // for (Chara chara : charas) chara.render(batch);
    batch.end();
  }
  public void dispose() {
    for (Chara chara : charas.data) {
      chara.onDeath();
    }
    batch.dispose();
  }
}
