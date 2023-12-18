package lib.math.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.Vector2;

import lib.math.Edge;

public class MST {
  public static ArrayList<Edge> primMST(ArrayList<Edge> edges) {
    HashSet<Vector2> visited = new HashSet<>();
    ArrayList<Edge> result = new ArrayList<>();
    
    PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingDouble(e -> 1d));
    minHeap.addAll(edges);

    Vector2 startNode = edges.get(0).p1;
    visited.add(startNode);

    while (!minHeap.isEmpty()) {
      Edge edge = minHeap.poll();
      Vector2 nextNode = visited.contains(edge.p1) ? edge.p2 : edge.p1;
 
      if (!visited.contains(nextNode)) {
        visited.add(nextNode);
        result.add(edge);
      }
    }

    return result;
  }
}

