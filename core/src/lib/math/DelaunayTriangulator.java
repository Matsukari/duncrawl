package lib.math;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;

public class DelaunayTriangulator {
  private com.badlogic.gdx.math.DelaunayTriangulator triangulator;
  private ShortArray triangleIndices;
  private FloatArray points;
  public DelaunayTriangulator(FloatArray points) {
    this.points = points;
    this.triangulator = new com.badlogic.gdx.math.DelaunayTriangulator();
    this.triangleIndices = triangulator.computeTriangles(points, false);

  }
  public ArrayList<Edge> getEdges() {
    ArrayList<Edge> edges = new ArrayList<>();
    for (int i = 0; i < triangleIndices.size; i+=3) {
      int p1 = triangleIndices.get(i+0) * 2;
      int p2 = triangleIndices.get(i+1) * 2;
      int p3 = triangleIndices.get(i+2) * 2;
      Vector2 a = new Vector2(points.get(p1), points.get(p1+1));
      Vector2 b = new Vector2(points.get(p2), points.get(p2+1));
      Vector2 c = new Vector2(points.get(p3), points.get(p3+1));
      edges.add(new Edge(a, b));
      edges.add(new Edge(b, c));
      edges.add(new Edge(c, a));
    }
    return edges;
  }
}
