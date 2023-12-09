package com.leisure.duncraw.map.generator;

import com.badlogic.gdx.math.Rectangle;

@FunctionalInterface
public interface RoomTileIterator {
  // x, and y relative to room; ie., row and col span
  public void onRoomAtTile(Rectangle room, int x, int y) throws Exception;
}
