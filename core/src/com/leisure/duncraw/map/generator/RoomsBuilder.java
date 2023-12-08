package com.leisure.duncraw.map.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.jgrapht.Graph;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

import lib.math.Circle;
import lib.math.DelaunayTriangulator;
import lib.math.Edge;

public class RoomsBuilder {
  public ArrayList<Edge> nodes = new ArrayList<>();
  public ArrayList<Edge> corridors = new ArrayList<>();
  public ArrayList<Rectangle> rooms = new ArrayList<>();
  public ArrayList<Rectangle> mainRooms = new ArrayList<>();
  public ArrayList<Rectangle> subRooms = new ArrayList<>();
  public HashMap<Rectangle, ArrayList<Rectangle>> roomConnections = new HashMap<>();
  public Vector2 roomsCenter = Vector2.Zero;
  public float mainRoomMinSize = 200;
  public int roomsNum;
  public int tileSize;
  public RoomsBuilder(int tileSize) {
    this.tileSize = tileSize;
  }
  public void build(int roomsNum, Vector2 maxSize, Vector2 widthRange, Vector2 heightRange) {
    this.roomsNum = roomsNum;
    mainRooms = genRandomRooms(roomsNum, maxSize, widthRange, heightRange);
    subRooms = genRandomRooms(roomsNum * 10, maxSize.sub(maxSize.x/3, maxSize.y/3), widthRange, heightRange);
    rooms.clear();
    rooms.addAll(mainRooms);
    separateRooms(mainRooms, roomsCenter, maxSize.x);
    separateRooms(subRooms, roomsCenter, maxSize.x);
    nodes = connectRooms(mainRooms);

    roomConnections = createPaths(nodes);
    corridors = createCorridors(roomConnections);
    
    ArrayList<Rectangle> trimmedSubRooms = new ArrayList<>();
    // Trim; delete rooms that aren't connected by corridors; ie., rooms that are unreachable
    for (Rectangle subRoom : subRooms) { for (Edge corridor : corridors) {
      if (Intersector.intersectSegmentRectangle(corridor.p1, corridor.p2, subRoom)) {
        trimmedSubRooms.add(subRoom);
        break;
      }
    } }
    subRooms = trimmedSubRooms;
    rooms.addAll(subRooms);
    // Logger.log("RoomsBuilder", String.format("Rooms %d left", rooms.size()));
  }
  // Main rooms scattered
  private ArrayList<Rectangle> genRandomRooms(int roomsNum, Vector2 maxSize, Vector2 widthRange, Vector2 heightRange) {
    ArrayList<Rectangle> newRooms = new ArrayList<>();
    Circle circle = new Circle(0, 0, 10);
    for (int it = 0; it < roomsNum; it ++) {
      Vector2 pos = circle.getRandomPoint();
      Rectangle rect = new Rectangle(pos.x, pos.y, 
        MathUtils.random(widthRange.x, widthRange.y) * maxSize.x, 
        MathUtils.random(heightRange.x, heightRange.y) * maxSize.y);
      convertToTile(rect);
      newRooms.add(rect);
    }
    return newRooms;
  }
  private void separateRooms(ArrayList<Rectangle> roomsDiv, Vector2 center, float boundary) {
    // Ensure no rooms are within the roomSize
    for (int it = 0; it < boundary*roomsDiv.size(); it++) {
      for (int i = 0; i < roomsDiv.size(); i++) {
        for (int j = 0; j < roomsDiv.size(); j++) {
          Rectangle a = roomsDiv.get(i), b = roomsDiv.get(j), inters = new Rectangle();
          if (j != i && Intersector.intersectRectangles(a, b, inters)) {
            float angle = MathUtils.atan2(center.y - b.y, center.x - b.x) * 180f / MathUtils.PI;
            b.x += MathUtils.cosDeg(angle) * inters.width; 
            b.y += MathUtils.sinDeg(angle) * inters.height;
            a.x -= MathUtils.cosDeg(angle) * inters.width;
            a.y -= MathUtils.sinDeg(angle) * inters.height;
          }
        }
      }
    }
    for (Rectangle room : roomsDiv) convertToTile(room);
  }
  private ArrayList<Edge> connectRooms(ArrayList<Rectangle> roomsDiv) {
    FloatArray points = new FloatArray();
    for (Rectangle rect : roomsDiv) {
      points.add((float)rect.x + rect.width/2);
      points.add((float)rect.y + rect.height/2);
    }
    return new DelaunayTriangulator(points).getEdges(); 
  }
  public ArrayList<Edge> createCorridors(HashMap<Rectangle, ArrayList<Rectangle>> rConnections) {
    ArrayList<Edge> newCorridors = new ArrayList<>(); 
    for (Map.Entry<Rectangle, ArrayList<Rectangle>> room : rConnections.entrySet()) {
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
        }
        // Doesn't overlap at one axis, then create another line going from initial line to destination room; guranteed intersection
        if (!Intersector.intersectSegmentRectangle(lineA.p1, lineA.p2, b)) {
          // Preivous default line was horizontal then now; vertical
          lineB = new Edge(new Vector2(lineA.p2.x, lineA.p2.y), new Vector2(lineA.p2.x, bY));
          // Previous changed line was vertical; then now; horizontal
          if (!horizontal) {
            lineB.p2.x = bX;
            lineB.p2.y = lineA.p2.y;
          }
        }
        newCorridors.add(lineA);
        if (lineB != null) newCorridors.add(lineB);
      } 
    }
    return newCorridors;
    // Logger.log("Created corridors", Integer.toString(corridors.size()));
  }
  private HashMap<Rectangle, ArrayList<Rectangle>> createPaths(ArrayList<Edge> paths) {
    HashMap<Rectangle, ArrayList<Rectangle>> rConnections = new HashMap<>();
    Graph<Vector2, DefaultWeightedEdge> graph = new SimpleGraph<>(DefaultWeightedEdge.class);
    for (Edge edge : paths) {
      graph.addVertex(edge.p1);
      graph.addVertex(edge.p2);
      graph.addEdge(edge.p1, edge.p2);
      Rectangle a = getRoomPointSrc(edge.p1);
      Rectangle b = getRoomPointSrc(edge.p2);
      ArrayList<Rectangle> connections = rConnections.get(a);
      
      if (connections == null) connections = new ArrayList<>();
      connections.add(b);

      rConnections.put(a, connections);
    }
    // Logger.log("Minimum", "Created " + Integer.toString(roomConnections.size()));
    paths.clear();
    PrimMinimumSpanningTree<Vector2, DefaultWeightedEdge> prim = new PrimMinimumSpanningTree<>(graph);
    for (DefaultWeightedEdge edge : prim.getSpanningTree().getEdges()) {
      paths.add(new Edge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge)));
    }
    return rConnections;
  }
  public float getTileValue(float x) {
    return MathUtils.floor(x / tileSize) * tileSize;
  }
  public void convertToTile(Vector2 pos) {
    pos.x = getTileValue(pos.x);
    pos.y = getTileValue(pos.y);
  }
  public void convertToTile(Rectangle rect) {
    rect.x = getTileValue(rect.x);
    rect.y = getTileValue(rect.y);
    rect.width = getTileValue(rect.width);
    rect.height = getTileValue(rect.height);
  }
  // on the basis that each rectangles does not overlap with any other
  public Rectangle getRoomPointSrc(Vector2 point) {
    for (Rectangle rect : rooms) {
      if (rect.contains(point.x, point.y)) return rect;
    }
    return null;
  }

}
