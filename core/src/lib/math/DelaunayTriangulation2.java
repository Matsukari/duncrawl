package lib.math;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;


public class DelaunayTriangulation2 {
  public static Triangle getSuperTriangle(ArrayList<Vector2> vertices) {
    float minx = 1000000, miny = 10000000;
    float maxx = -100000, maxy = -100000;
    for (Vector2 vertex : vertices) {
      minx = Math.min(minx, vertex.x);
      miny = Math.min(minx, vertex.y);
      maxx = Math.max(maxx, vertex.x);
      maxy = Math.max(maxx, vertex.y);
    }

    float dx = (maxx - minx) * 10;
    float dy = (maxy - miny) * 10;

    Vector2 v0 = new Vector2(minx - dx, miny - dy * 3);
    Vector2 v1 = new Vector2(minx - dx, maxy + dy);
    Vector2 v2 = new Vector2(maxx + dx * 3, maxy + dy);

    return new Triangle(v0, v1, v2);
  }
  public static ArrayList<Triangle> triangulate(ArrayList<Vector2> pointList) {
    ArrayList<Triangle> triangulation = new ArrayList<>();
    // Initialize the super triangle with vertices outside the convex hull
    // Vector2 p1 = new Vector2(-1000, -1000);
    // Vector2 p2 = new Vector2(3000, -1000);
    // Vector2 p3 = new Vector2(0, 3000);
    // Triangle superTriangle = new Triangle(p1, p2, p3);
    Triangle superTriangle = getSuperTriangle(pointList);
    triangulation.add(superTriangle);

    
    for (Vector2 point : pointList) {
      ArrayList<Triangle> badTriangles = new ArrayList<>();

      // Find all triangles that are no longer valid with the new point
      for (Triangle triangle : triangulation) {
        if (pointInsideCircumcircle(point, triangle)) {
          badTriangles.add(triangle);
        }
      }


      // Create new triangles formed by the edges of the removed triangles and the new point
      List<Edge> boundaryEdges = new ArrayList<>();
      for (Triangle triangle : badTriangles) {
        for (int i = 0; i < 3; i++) {
          Edge edge = triangle.getEdge(i);
          boolean shared = false;
          // for (Edge bEdge : boundaryEdges) 
            // if ((bEdge.p1.equals(edge.p1) && bEdge.p2.equals(edge.p2)) || (bEdge.p1.equals(edge.p2) && bEdge.p2.equals(edge.p1))) ok = false;

          for (Triangle badTriangle : badTriangles) {
            for (int j = 0; j < 3; j++) {
              if (!badTriangle.equals(triangle) && badTriangle.getEdge(j).equals(edge)) shared = true;
            }
          }
          if (!shared) boundaryEdges.add(edge);
          
        }
      }
      // Remove invalid triangles from triangulation
      for (int i = 0; i < badTriangles.size(); i++) {
        triangulation.remove(badTriangles.get(i));
      }

      for (Edge edge : boundaryEdges) {
        triangulation.add(new Triangle(edge.p1, edge.p2, point));
      }

      for (int x = 0; x < triangulation.size(); x++) {
        boolean contains = false;
        for (int i = 0; i < 3; i++) { 
          for (int j = 0; j < 3; j++) 
          if (triangulation.get(x).points[i].equals(superTriangle.points[j])) contains = true;
        }
        if (contains) triangulation.remove(x);
      }
    }
    // triangulation.remove(superTriangle);
    return triangulation;
  }
  public static ArrayList<Edge> getEdges(ArrayList<Triangle> triangulation) {
    ArrayList<Edge> edges = new ArrayList<>();
    for (Triangle triangle : triangulation) {
      for (int i = 0; i < 3; i++) 
        if (!edges.contains(triangle.getEdge(i))) edges.add(triangle.getEdge(i));
    }
    return edges;
  }

  private static boolean pointInsideCircumcircle(Vector2 point, Triangle triangle) {
    double x1 = triangle.points[0].x;
    double y1 = triangle.points[0].y;
    double x2 = triangle.points[1].x;
    double y2 = triangle.points[1].y;
    double x3 = triangle.points[2].x;
    double y3 = triangle.points[2].y;

    double D = (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3);
    double center_x = ((x1 - x3) * (x1 + x3) + (y1 - y3) * (y1 + y3)) / (2 * D);
    double center_y = ((x2 - x3) * (x2 + x3) + (y2 - y3) * (y2 + y3)) / (2 * D);

    double radiusSquared = Math.pow(x1 - center_x, 2) + Math.pow(y1 - center_y, 2);

    double distanceSquared = Math.pow(point.x - center_x, 2) + Math.pow(point.y - center_y, 2);

    return distanceSquared <= radiusSquared;
  }

}
