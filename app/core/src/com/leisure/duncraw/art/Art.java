package com.leisure.duncraw.art;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.leisure.duncraw.helper.IdGenerator;

public abstract class Art {
  public transient final int id;
  public transient Rectangle bounds = new Rectangle(0, 0, 100f, 100f);
  public transient Color tint = new Color(1f, 1f, 1f, 1f);
  public Art() {
    id = IdGenerator.gen();
  }
  public void centerTo(Art other) {
    bounds.x = other.getWorldX() - bounds.width/2;
    bounds.y = other.getWorldY() - bounds.height/2;
    bounds.x += other.bounds.width/2;
    bounds.y += other.bounds.height/2;
  }
  public float getWorldX() { return bounds.x; }
  public float getWorldY() { return bounds.y; }
  @Override
  public boolean equals(Object obj) {
    return id == ((Art)obj).id;
  }
  public void render(SpriteBatch batch) {}

}
