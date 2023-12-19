package lib.math;

public class Pointi {
  public int x;
  public int y;
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
  

}
