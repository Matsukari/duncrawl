package lib.math;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Pointi {
  public int x;
  public int y;
  public Pointi() {}
  public Pointi(int x, int y) { 
    this.x = x;
    this.y = y;
  }
  public Pointi(Pointi point) {
    x = point.x;
    y = point.y;
  }
  @Override
  public boolean equals(Object obj) {
    Pointi point = (Pointi)obj;
    return x == point.x && y == point.y;
  }
  @Override
  public int hashCode() {
    return y * 1000 + x;
  }
  public Pointi set(int x, int y) { 
    this.x = x;
    this.y = y;
    return this;
  }
  public static Pointi getRandom(Rectangle rectangle) {
    Pointi point = new Pointi();
    point.x = (int)MathUtils.random(rectangle.x, rectangle.x+rectangle.width);
    point.y = (int)MathUtils.random(rectangle.y, rectangle.y+rectangle.height);
    return point;
  }  

}
