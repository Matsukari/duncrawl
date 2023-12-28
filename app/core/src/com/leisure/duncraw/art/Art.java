package com.leisure.duncraw.art;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.leisure.duncraw.helper.IdGenerator;

public abstract class Art {
  public transient final int id;
  public transient Rectangle bounds = new Rectangle(0, 0, 100f, 100f);
  public Art() {
    id = IdGenerator.gen();
  }
  public void centerTo(Rectangle other) {
    bounds.x = other.x - bounds.width/2;
    bounds.y = other.y - bounds.height/2;
    bounds.x += other.width/2;
    bounds.y += other.height/2;
  }
  @Override
  public boolean equals(Object obj) {
    return id == ((Art)obj).id;
  }
  public void render(SpriteBatch batch) {}

}
