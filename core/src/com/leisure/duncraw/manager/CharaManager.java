package com.leisure.duncraw.manager;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
  public ArrayList<Chara> charas = new ArrayList<>();
  public SpriteBatch batch = new SpriteBatch();
  public Chara player;
  private final Floor floor;
  public final CharasData sources;
  public final ArrayList<Observer> observers = new ArrayList<>();
  public CharaManager(CharasData sources, Floor floor) { 
    this.sources = sources;
    this.floor = floor;
    observers.add(new AttackBehaviour());
    observers.add(new HurtBehaviour());
    observers.add(new SoundBehaviour());
  }

  public <T extends Chara> T addFrom(String source, Class<T> clazz) {
    Logger.log("CharaManager", "Creating a character from source: " + source);
    CharaData data = new CharaData();
    data.reset();
    try { data = Deserializer.load(CharaData.class, Gdx.files.local(source)); }
    catch(Exception e) { Serializer.save(data, Gdx.files.local(source)); }
    try {
      T chara = clazz.getDeclaredConstructor(CharaData.class, SpriteBatch.class).newInstance(data, batch);
      chara.bounds.setSize(32, 32);
      chara.mapAgent = new TilemapChara(chara, floor);
      floor.putChara(chara.mapAgent); 
      charas.add(chara);
      for (Observer observer : observers) chara.observers.add(observer.copy());
      return chara;  
    } catch (Exception e) { e.printStackTrace(); }
    return null;
  }
  public Chara addFrom(String source) { return addFrom(source, Chara.class); }
  public void kill(int id) {}
  public void updateAll(float dt) {
    for (Chara chara : charas) {
      chara.update(dt);
    }
  }
  public void renderAll(Camera cam) {
    batch.setProjectionMatrix(cam.combined);
    batch.begin();
    for (Chara chara : charas) chara.render();
    batch.end();
  }
}
