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
import lib.math.Pointi;

// Gods, you must believe if I tell you how much TIME I spent to caress this already BIG BOY!
public class RoomsBuilder {
  public ArrayList<Edge> nodes = new ArrayList<>();
  public ArrayList<Edge> corridors = new ArrayList<>();
  public ArrayList<Rectangle> rooms = new ArrayList<>();
  public ArrayList<Rectangle> mainRooms = new ArrayList<>();
  public ArrayList<Rectangle> subRooms = new ArrayList<>();
  public HashMap<Rectangle, ArrayList<Rectangle>> roomConnections = new HashMap<>();
  public Vector2 roomsCenter = new Vector2(1000, 1000);
  public Rectangle rect = new Rectangle();
  public Vector2 min = new Vector2();
  public Vector2 max = new Vector2();
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
    // After the rooms are finalized
    rect = getRect();
    // Logger.log("RoomsBuilder", String.format("Rooms %d left", rooms.size()));
  }
  // Main rooms scattered
  private ArrayList<Rectangle> genRandomRooms(int roomsNum, Vector2 maxSize, Vector2 widthRange, Vector2 heightRange) {
    ArrayList<Rectangle> newRooms = new ArrayList<>();
    Circle circle = new Circle(roomsCenter.x, roomsCenter.y, 10);
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
  // Gods, may the corridors (line) that are passed onto this totem straighten themselves, the line MUST ONLY GO FROM ONE DIRECTION
  // either horizontal or vertical, never diagonal
  public ArrayList<Rectangle> expandCorridors(int span, boolean center) { 
    assert (span < 1 || span >= 100);
    ArrayList<Rectangle> expanded = new ArrayList<>();
    span *= tileSize;
    for (Edge corridor : corridors) {
      Vector2 start = new Vector2(corridor.left().x, corridor.bottom().y);
      float disX = corridor.p2.x - corridor.p1.x; 
      float disY = corridor.p2.y - corridor.p1.y; 
      float width = 0;
      float height = 0;
      Vector2 breadth = new Vector2();
      String type = "Vertical";
      // One axis must be aligned to the other one, going ONLY to ONE direction
      assert disX == 0 || disY == 0;
      // Corridor is vertical
      if (Math.abs(disX) < Math.abs(disY)) {
        breadth.x = span;
        width = breadth.x;
        height = Math.abs(disY);
      } // Horizontal
      else {
        type = "Horizontal";
        breadth.y = span;
        height = breadth.y;
        width = Math.abs(disX);
      }
    
      if (center) {
        start.x -= breadth.x / 2;
        start.y -= breadth.y / 2;
      } 
      expanded.add(new Rectangle(start.x, start.y, width, height));

      // How much would I need to stain my hands? 
      Logger.log("RoomsBuilder", 
          String.format("<%2d><%10s> (%-4d %-4d %-4d %-4d) - edge A(%-4d %-4d ) B (%-4d %-4d )  ==   start (%-4d %-4d ) -> distance (%-4d %-4d )", 
            expanded.size() , 
            type,
            (int)expanded.get(expanded.size()-1).x ,  
            (int)expanded.get(expanded.size()-1).y ,  
            (int)expanded.get(expanded.size()-1).width ,  
            (int)expanded.get(expanded.size()-1).height,
            (int)corridor.p1.x,
            (int)corridor.p1.y,
            (int)corridor.p2.x,
            (int)corridor.p2.y,
            (int)start.x,
            (int)start.y,
            (int)disX,
            (int)disY
          ));
    }
    return expanded;
  }
  public void forEachTileInRooms(ArrayList<Rectangle> roomsDiv, RoomTileIterator iterator) throws Exception {
    int i = 0, j = 0;
    for (Rectangle room : roomsDiv) {
      int cols = (int)(room.width / tileSize);
      int rows = (int)(room.height / tileSize);
      // Logger.log("Room", Integer.toString(i) + " - " + room.toString());
      i++;
      for (int col = 0; col < cols; col++) {
        for (int row = 0; row < rows; row++) { 
          iterator.onRoomAtTile(room, col, row);
          j++;
        }
      }
    }
    Logger.log("RoomsBuilder", "finished generating " + Integer.toString(j) + " tiles");
  }
  public float getTileValue(float x) {
    return MathUtils.floor(x / tileSize) * tileSize;
  }
  public void convertToTile(Rectangle rect) {
    rect.x = getTileValue(rect.x);
    rect.y = getTileValue(rect.y);
    rect.width = getTileValue(rect.width);
    rect.height = getTileValue(rect.height);
  }
  // Get the space enclosing all of the rooms as a rectangle, positions vary and stay as it is... It's obviously bottom-left oriented
  // Used to make the return axes the standard for operation.  
  public Rectangle getRect() {
    Rectangle rect = new Rectangle();
    min = new Vector2(100000, 100000);
    max = new Vector2(-10000, -10000);
    for (Rectangle room : rooms) { 
      min.x = Math.min(min.x, room.x);
      min.y = Math.min(min.y, room.y);
      max.x = Math.max(max.x, room.x + room.width);
      max.y = Math.max(max.y, room.y + room.height);
    }
    rect.x = min.x;
    rect.y = min.y;
    rect.width = (int)((max.x - min.x) / tileSize) * tileSize;
    rect.height = (int)((max.y - min.y) / tileSize) * tileSize;
    // rect.width = Math.abs(rect.width);
    // rect.height = Math.abs(rect.height);
    return rect;
  }
  // Returns grid position relative to room, never < 0
  public Pointi getRoomRelTilePos(Rectangle room) {
    // Assume that the minimuma and maximum position (getRect) has been called or processed validly
    assert rect.x > room.x && rect.y > room.y;
    Pointi pos = new Pointi(
      (int)((room.x - rect.x) / tileSize), 
      (int)((room.y - rect.y) / tileSize) );
    // pos.y = rect.height / tileSize - pos.y;
    return pos;
  }
  // on the basis that each rectangles does not overlap with any other
  public Rectangle getRoomPointSrc(Vector2 point) {
    for (Rectangle rect : rooms) {
      if (rect.contains(point.x, point.y)) return rect;
    }
    return null;
  }

}


/*    SAMPLE DATA
 *
 */
