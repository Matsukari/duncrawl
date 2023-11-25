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
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.data.CharasData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;

import lib.animation.LinearAnimation;

public class CharaManager {
  public ArrayList<Chara> charas = new ArrayList<>();
  public SpriteBatch batch = new SpriteBatch();
  public Chara player;
  private final Floor floor;
  public CharaManager(CharasData sources, Floor floor) { 
    this.floor = floor;
    player = addFrom(sources.player_idle);
  }

  public Chara addFrom(String source) {
    Logger.log("CharaManager", "Creating a character from source: " + source);
    Chara chara = new Chara(new LinearAnimation<TextureRegion>(0.1f, 
      new Array<TextureRegion>(TextureRegion.split(new Texture(Gdx.files.local(source)), 16, 16)[0]), PlayMode.LOOP), batch);
    chara.bounds.setSize(32, 32);
    chara.mapAgent = new TilemapChara(chara, floor);
    floor.putChara(chara.mapAgent); 
    charas.add(chara);
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
