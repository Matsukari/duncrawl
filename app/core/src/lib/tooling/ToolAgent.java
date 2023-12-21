package lib.tooling;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class ToolAgent {
  public String id = "Tool";
  public Vector2 size = new Vector2(200, 200);
  public ToolAgent(String id) { this.id = id; }
  public ToolAgent() { }
  public void tool() {}
  public void render(ShapeRenderer renderer) {}
  public void render(SpriteBatch batch) {}
  @Override
  public boolean equals(Object obj) {
    return ((ToolAgent)obj).id == id;
  }
}
