package com.leisure.duncraw.art;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Art {
  public final int id;
  public Color color = new Color(1, 1, 1, 1);
  public Rectangle bounds = new Rectangle(0, 0, 100f, 100f);
  public final SpriteBatch batch;
  public Art(SpriteBatch batch) {
    id = IdGenerator.gen();
    this.batch = batch;
  }
  @Override
  public boolean equals(Object obj) {
    return id == ((Art)obj).id;
  }
  public void moveTo(float x, float y) { bounds.setPosition(x, y); }
  public void render() {}
  private static class IdGenerator {
    private static int last = 0;
    public static int gen() { 
      // Logger.log("IdGenerator", "Generated id for unique resource");
      last += 1; 
      return last;
    }
  }

}
