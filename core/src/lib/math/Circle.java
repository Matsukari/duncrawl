package lib.math;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Circle {
  public float x;
  public float y;
  public float radius;
  public Circle(float x, float y, float radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
  }
  public Vector2 getRandomPoint() { 
    double t = 2 * Math.PI * MathUtils.random();
    double u = Math.random() + MathUtils.random();
    double r = 0;
    if (u > 1) r = 2-u;
    else r = u;
    return new Vector2(x + radius * (float)(r * Math.cos(t)), y + radius * (float)(r * Math.sin(t)));
  }
}
