package com.leisure.duncraw.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.leisure.duncraw.art.chara.CharaSpawner;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.map.Terrain;

public class Room {
  public float x, y; 
  public CharaSpawner spawner;
  public final Tilemap map;
  public final FrameBuffer minimap;
  private final Player player;
  private final ShapeRenderer renderer;
  public Room(Player player, Tilemap map) {
    this.player = player;
    this.map = map;
    renderer = new ShapeRenderer();
    minimap = new FrameBuffer(Format.RGBA8888, 100, 100, false);
  }
  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public void update() {

  }
  public void renderMinimap(float mpX, float mpY) {
    renderer.begin();
    renderer.circle(
      ((float)((int)(player.bounds.x/Gdx.graphics.getWidth()*100f))) + mpX,
      ((float)((int)(player.bounds.y/Gdx.graphics.getHeight()*100f))) + mpY,
      5f
    );
    renderer.end();
  }
  public void render() {
    map.render();
    for (Terrain terrain : map.terrainSet.terrains) {
      if (terrain != null) terrain.bounds.setPosition(x + terrain.bounds.x, y + terrain.bounds.y);
    }
  }
}
