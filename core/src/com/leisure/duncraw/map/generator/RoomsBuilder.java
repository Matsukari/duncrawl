package com.leisure.duncraw.map.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.leisure.duncraw.logging.Logger;

import lib.math.Circle;
import lib.math.DelaunayTriangulator;
import lib.math.Edge;

public class RoomsBuilder {
  public ArrayList<Edge> nodes = new ArrayList<>();
  public ArrayList<Edge> corridors = new ArrayList<>();
  public ArrayList<Rectangle> rooms = new ArrayList<>();
  public ArrayList<Rectangle> mainRooms = new ArrayList<>();
  public HashMap<Rectangle, ArrayList<Rectangle>> roomConnections = new HashMap<>();
  public Vector2 roomsCenter = Vector2.Zero;
  public float mainRoomMinSize = 200;
  public int roomsNum;

  public void build(int roomsNum, Vector2 maxSize, Vector2 widthRange, Vector2 heightRange) {
    this.roomsNum = roomsNum;
    genRandomRooms(maxSize, widthRange, heightRange);
    separateRooms(maxSize.x);
    connectRooms();

    createPaths();
    createCorridors();
  }
  private void genRandomRooms(Vector2 maxSize, Vector2 widthRange, Vector2 heightRange) {
    rooms.clear();
    mainRooms.clear();
    Circle circle = new Circle(0, 0, 10);
    for (int it = 0; it < roomsNum; it ++) {
      Vector2 pos = circle.getRandomPoint();
      Rectangle rect = new Rectangle(pos.x, pos.y, 
        MathUtils.random(widthRange.x, widthRange.y) * maxSize.x, 
        MathUtils.random(heightRange.x, heightRange.y) * maxSize.y);
      rooms.add(rect);     
      if (rect.width + rect.height >= mainRoomMinSize) mainRooms.add(rect);
    }
  }
  private void separateRooms(float boundary) {
    // Ensure no rooms are within the roomSize
    for (int it = 0; it < boundary*rooms.size(); it++) {
      for (int i = 0; i < rooms.size(); i++) {
        for (int j = 0; j < rooms.size(); j++) {
          Rectangle a = rooms.get(i), b = rooms.get(j), inters = new Rectangle();
          if (j != i && Intersector.intersectRectangles(a, b, inters)) {
            float angle = MathUtils.atan2(roomsCenter.y - b.y, roomsCenter.x - b.x) * 180f / MathUtils.PI;
            b.x += MathUtils.cosDeg(angle) * inters.width; 
            b.y += MathUtils.sinDeg(angle) * inters.height;
            a.x -= MathUtils.cosDeg(angle) * inters.width;
            a.y -= MathUtils.sinDeg(angle) * inters.height;
          }
        }
      }
    }
  }
  private void connectRooms() {
    FloatArray points = new FloatArray();
    for (Rectangle rect : rooms) {
      points.add((float)rect.x + rect.width/2);
      points.add((float)rect.y + rect.height/2);
    }
    nodes = new DelaunayTriangulator(points).getEdges(); 
  }
  public void createCorridors() {
    corridors.clear(); 
    for (Map.Entry<Rectangle, ArrayList<Rectangle>> room : roomConnections.entrySet()) {
      // Logger.log("createCorridors", "Creating a corridor...");
      Rectangle a = room.getKey();
      float aX = a.x + a.width/2;
      float aY = a.y + a.height/2;
      for (Rectangle b : room.getValue()) {
        float bX = b.x + b.width/2;
        float bY = b.y + b.height/2;
        float distanceX = bX - aX;
        float distanceY = bY - aY;
        // Default horizontal line
        Edge lineA = new Edge(new Vector2(aX, aY), new Vector2(bX, aY));
        Edge lineB = null;
        boolean horizontal = true;
        // Nearer in Y-axis; then vertical line
        if (Math.abs(distanceY) < Math.abs(distanceX)) {
          lineA.p2.x = aX;
          lineA.p2.y = bY;
          horizontal = false;
          // Logger.log("First line", "Created vertical line");
        }
        // else 
        //   Logger.log("Intersector", "Created horizontal line");
        // Rectangle intersection = new Rectangle();
        // Doesn't overlap at one axis, then create another line going from initial line to destination room; guranteed intersection
        if (!Intersector.intersectSegmentRectangle(lineA.p1, lineA.p2, b)) {
          // Preivous default line was horizontal then now; vertical
          lineB = new Edge(new Vector2(lineA.p2.x, lineA.p2.y), new Vector2(lineA.p2.x, bY));
          // Previous changed line was vertical; then now; horizontal
          if (!horizontal) {
            lineB.p2.x = bX;
            lineB.p2.y = lineA.p2.y;
            // Logger.log("Intersector", "Created another horizontal line");
          }
          // else
          //   Logger.log("Intersector", "Created another vertical line");
        }
        corridors.add(lineA);
        if (lineB != null) corridors.add(lineB);
      } 
    }
    Logger.log("Created corridors", Integer.toString(corridors.size()));
  }
  private void createPaths() {
    roomConnections.clear();
    Graph<Vector2, DefaultWeightedEdge> graph = new SimpleGraph<>(DefaultWeightedEdge.class);
    for (Edge edge : nodes) {
      graph.addVertex(edge.p1);
      graph.addVertex(edge.p2);
      graph.addEdge(edge.p1, edge.p2);
      Rectangle a = getRoomPointSrc(edge.p1);
      Rectangle b = getRoomPointSrc(edge.p2);
      ArrayList<Rectangle> connections = roomConnections.get(a);
      
      if (connections == null) connections = new ArrayList<>();
      connections.add(b);

      roomConnections.put(a, connections);
    }
    // Logger.log("Minimum", "Created " + Integer.toString(roomConnections.size()));
    nodes.clear();
    PrimMinimumSpanningTree<Vector2, DefaultWeightedEdge> prim = new PrimMinimumSpanningTree<>(graph);
    for (DefaultWeightedEdge edge : prim.getSpanningTree().getEdges()) {
      nodes.add(new Edge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge)));
    }
  }
  // on the basis that each rectangles does not overlap with any other
  public Rectangle getRoomPointSrc(Vector2 point) {
    for (Rectangle rect : rooms) {
      if (rect.contains(point.x, point.y)) return rect;
    }
    return null;
  }

}
