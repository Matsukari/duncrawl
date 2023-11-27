package com.leisure.duncraw.manager;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Array;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.observers.AnimationReactor;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.CharasData;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;

import lib.animation.LinearAnimation;

public class CharaManager {
  public ArrayList<Chara> charas = new ArrayList<>();
  public SpriteBatch batch = new SpriteBatch();
  public Chara player;
  public final CharasData sources;
  private final Floor floor;
  public CharaManager(CharasData sources, Floor floor) { 
    this.sources = sources;
    this.floor = floor;
  }

  public Chara addFrom(String source) {
    Logger.log("CharaManager", "Creating a character from source: " + source);
    CharaData data = new CharaData();
    data.reset();
    try { data = Deserializer.load(CharaData.class, Gdx.files.local(source)); }
    catch(Exception e) { Serializer.save(data, Gdx.files.local(source)); }
    Chara chara = new Chara(data, batch);
    chara.bounds.setSize(32, 32);
    chara.mapAgent = new TilemapChara(chara, floor);
    floor.putChara(chara.mapAgent); 
    charas.add(chara);
    chara.observers.add(new AnimationReactor());
    return chara;
  
  }
  public void updateAll(float dt) {
    for (Chara chara : charas) {
      chara.update(dt);
    }
  }
  public void renderAll() {
    batch.begin();
    for (Chara chara : charas) chara.render();
    batch.end();
  }
}
