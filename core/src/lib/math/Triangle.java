package lib.math;

import com.badlogic.gdx.math.Vector2;

public class Triangle {
  public Vector2[] points = new Vector2[3];
  public Triangle(Vector2 p1, Vector2 p2, Vector2 p3) {
    points[0] = p1;
    points[1] = p2;
    points[2] = p3;
  }
  // Returns the edge opposite to the given vertex index
  public Edge getEdge(int vertexIndex) {
    int nextIndex = (vertexIndex + 1) % 3;
    return new Edge(points[vertexIndex], points[nextIndex]);
  }
  @Override
  public boolean equals(Object obj) {
    Triangle b = (Triangle)obj;
    return points[0] == b.points[0] && points[1] == b.points[1] && points[2] == b.points[2];
  }
  // Helper method to calculate the circumcenter of the triangle
  private Vector2 getCircumcenter() {
    double D = 2 * (points[0].x * (points[1].y - points[2].y) + points[1].x * (points[2].y - points[0].y) + points[2].x * (points[0].y - points[1].y));
    double Ux = ((points[0].x * points[0].x + points[0].y * points[0].y) * (points[1].y - points[2].y) +
  (points[1].x * points[1].x + points[1].y * points[1].y) * (points[2].y - points[0].y) +
  (points[2].x * points[2].x + points[2].y * points[2].y) * (points[0].y - points[1].y)) / D;

    double Uy = ((points[0].x * points[0].x + points[0].y * points[0].y) * (points[2].x - points[1].x) +
  (points[1].x * points[1].x + points[1].y * points[1].y) * (points[0].x - points[2].x) +
  (points[2].x * points[2].x + points[2].y * points[2].y) * (points[1].x - points[0].x)) / D;

    return new Vector2((float)Ux, (float)Uy);
  }

  // Helper method to calculate the circumradius of the triangle
  private double getCircumradius(Vector2 circumcenter) {
    return Math.sqrt((circumcenter.x - points[0].x) * (circumcenter.x - points[0].x) +
    (circumcenter.y - points[0].y) * (circumcenter.y - points[0].y));
  }

  // Helper method to check if a point is inside the circumcircle of the triangle
  public boolean isInsideCircumcircle(Vector2 point) {
    double ax = points[0].x - point.x;
    double ay = points[0].y - point.y;
    double bx = points[1].x - point.x;
    double by = points[1].y - point.y;
    double cx = points[2].x - point.x;
    double cy = points[2].y - point.y;
    double dx = ax - point.x;
    double dy = ay - point.y;
    double ex = bx - point.x;
    double ey = by - point.y;
    double fx = cx - point.x;
    double fy = cy - point.y;

    double ap = dx * dx + dy * dy;
    double bp = ex * ex + ey * ey;
    double cp = fx * fx + fy * fy;

    return dx * (ey * cp - bp * fy) -
    dy * (ex * cp - bp * fx) +
    ap * (ex * fy - ey * fx) < 0;
  }
  // public boolean isInsideCircumcircle(Vector2 point) {
  //   double ax = points[0].x - point.x;
  //   double ay = points[0].y - point.y;
  //   double bx = points[1].x - point.x;
  //   double by = points[1].y - point.y;
  //   double cx = points[2].x - point.x;
  //   double cy = points[2].y - point.y;
  //
  //   double ab = ax * (points[0].x + point.x) + ay * (points[0].y + testPoint.y);
  //   double bc = bx * (points[1].x + point.x) + by * (points[1].y + testPoint.y);
  //   double ca = cx * (points[2].x + point.x) + cy * (points[2].y + testPoint.y);
  //
  //   double det = ax * (by - cy) + bx * (cy - ay) + cx * (ay - by);
  //
  //   return det > 0 && ab * (by - cy) + bc * (cy - ay) + ca * (ay - by) > 0;
  // }
  // Other methods for checking orientation, circumcircle, etc., can be added here
}
