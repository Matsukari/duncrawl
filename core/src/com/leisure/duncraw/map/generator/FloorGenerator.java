package com.leisure.duncraw.map.generator;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.Room;

public class FloorGenerator {
  public ArrayList<Room> reqRooms;
  public RoomsBuilder roomsBuilder;
  public boolean consumeReq = true;
  public static class WallSet {
    public ArrayList<TextureRegion> heads = new ArrayList<>();
    public ArrayList<TextureRegion> textures = new ArrayList<>();
    public static String headsOrder = "><-|";
  }
  public FloorGenerator() {
    roomsBuilder = new RoomsBuilder();
  }
  public Floor gen() {
    /*    
     *      BASIC RECTANGULAR STRUCTURED INTERCONNECTED ROOMS
     *
     * Create outline of the whole floor
     * Determine main rooms
     * Connect paths from entrance to farthest node main room
     *
     *      ROOMS
     * 
     * Place wall texture based on depth of the walls on every edges of rooms
     *  * Place head texture on the topmost walls
     *  * If at corner on heads - place relevant texture (top-right, top-left, bottom-left, bottom-right)
     *  * Place relevant shadow overlay on the edges of walls texture
     * Place ground texture on every blocks surrounded by walls
     * Place relevant shadow overylay on edge corners
     *
     *
     * 
     *      OBJECTS
     *
     * Place a random object 
     *
     *
     *      CONNECTING THE ROOMS INTO ONE 
     *
     * Place prefabs next to random rooms such that two or more prefabs are distanced from each other
     * Pick a starting room where an Opening Stair obj is randomly placed
     * Pick an ending room where an Exit Stair obj is randomly placed
     * Connect all rooms into one and create an interconnected paths that leads to relevant rooms 
     *
     *      MAKING MINI MAP 
     * 
     * Create a framebuffer that you can render mini map 
     * Filter rooms to a minimum size that will be created a mini map 
     * Draw a rectangle on framebuffer for every selected rooms, with minimized size and position relative on a grid
     * The rendering will only be done in loading screen once and be rendered by other system components
     *
     * As for mob and charas representation
     *  * Place a circle on a minimized grid created using the position of the placer
     *
     * */
    return new Floor(TerrainSetGenerator.gen(1), null);
  }
}
