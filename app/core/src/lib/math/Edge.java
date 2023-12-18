package lib.math;

import com.badlogic.gdx.math.Vector2;

public class Edge {
  public Vector2 p1, p2;

  public Edge(Vector2 p1, Vector2 p2) {
    this.p1 = p1;
    this.p2 = p2;
  }
  public boolean isBoundary() {
    // Implement the check for whether the edge is a boundary edge
    return true;
  }
  @Override
  public boolean equals(Object obj) {
    // if (this == obj) return true;
    // if (obj == null || getClass() != obj.getClass()) return false;
    Edge edge = (Edge) obj;
    return (p1.equals(edge.p1) && p2.equals(edge.p2)) || (p1.equals(edge.p2) && p2.equals(edge.p1));
  }
  public double distance() {
    return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
  }
  public Vector2 left() { return (p1.x < p2.x) ? p1 : p2; }
  public Vector2 right() { return (p2.x > p1.x) ? p2 : p1; }
  public Vector2 bottom() { return (p2.y < p1.y) ? p2 : p1; }
  public Vector2 top() { return (p1.y > p2.y) ? p1 : p2; }

  @Override
  public int hashCode() {
    return p1.hashCode() + p2.hashCode();
  }
}
