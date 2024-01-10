package com.leisure.duncraw.data;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

public class FloorStatistic extends Dat {
  public ArrayList<Rectangle> visitedRooms; 
  @Override
  public void reset() {
    visitedRooms = new ArrayList<>();

  }  
  
}
