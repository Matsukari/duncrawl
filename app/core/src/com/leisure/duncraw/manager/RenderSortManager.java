package com.leisure.duncraw.manager;

import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.EntityGroup;
import com.leisure.duncraw.art.map.Decoration;

public class RenderSortManager {
  public final ArrayList<Art> entities = new ArrayList<>();
  public final SpriteBatch batch = new SpriteBatch();
  public <T extends Art> EntityGroup<T> newEntities(Class<T> clazz) { return new EntityGroup<T>(this); }

  public void renderAll(Camera camera) {
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    // entities.sort((a, b)-> {
    //   if (a instanceof Decoration) return -1;
    //   else if (b instanceof Decoration) return 1;
    //   if (a.getWorldY() > b.getWorldY()) return -1;
    //   else if (a.getWorldY() < b.getWorldY()) return 1;
    //   return 0;
    // 
    // }); 
    entities.sort((a, b)-> 
          (a.getWorldY() > b.getWorldY() ) ? -1 
        : (b.getWorldY() > a.getWorldY() ) ? 1
        : 0 );
    synchronized (entities) {
      for (Art entity : entities) {
        entity.render(batch);
      }
    }
    batch.end();
  }
  
}
