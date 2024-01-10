package com.leisure.duncraw.debug.editor;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.lighting.PointLight;
import com.leisure.duncraw.manager.FloorManager;
import com.leisure.duncraw.map.floors.Floor1;
import com.leisure.duncraw.map.generator.RoomsBuilder;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

import imgui.ImGui;
import imgui.type.ImInt;
import lib.math.Edge;
import lib.tooling.ToolAgent;

public class FloorEditor extends ToolAgent {
  public ImInt points = new ImInt(3);
  public RoomsBuilder roomsBuilder;
  public TerrainSetGenerator generator;
  public FloorManager manager;
  public BitmapFont font;
  public ArrayList<PointLight> lights = new ArrayList<>();
  public boolean hideShapes = true;
  public FloorEditor(FloorManager manager) {
    super("FloorEditor");
    this.manager = manager;
    this.generator = manager.getFloor().generator;
    this.roomsBuilder = generator.roomsBuilder;
    generator.roomsBuilder = roomsBuilder;
    font = Graphics.getFont(Graphics.fontSources.def);
    lights.addAll(manager.lighting.getEnv().lightSources);
    manager.lighting.getEnv().lightSources.clear();
  }
  @Override
  public void tool() {
    ImGui.labelText("Visited rooms", Integer.toString(manager.getFloor().generator.data.statistic.visitedRooms.size()));
    ImGui.inputInt("Rooms", points);
    if (ImGui.button("Toogle shapes")) {
      hideShapes = !hideShapes;
    }
    if (ImGui.button("Generate")) {
      // roomsBuilder.build(points.get(), new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), new Vector2(0.2f, 1f), new Vector2(0.2f, 1f));
      // generator.data.roomsNum = points.get(); 
      // manager.setCurrentFloor(new Floor1(generator, manager.batch));
    }
    if (ImGui.button("Toogle lighting")) {
      manager.showLighting = !manager.showLighting;  
    }
    else if (ImGui.button("Toogle lights")) {

      if (manager.lighting.getEnv().lightSources.isEmpty()) {
        manager.lighting.getEnv().lightSources.addAll(lights);
      }
      else manager.lighting.getEnv().lightSources.clear();
    }
  }
  @Override
  public void render(ShapeRenderer renderer) {
    if (hideShapes) return;
    for (Edge e : roomsBuilder.nodes) {
      Edge edge = new Edge(e);
      edge.p1.x /= 10;
      edge.p1.y /= 10;
      edge.p2.x /= 10;
      edge.p2.y /= 10;
      renderer.begin(ShapeType.Line);
      renderer.setColor(Color.DARK_GRAY);
      renderer.line(edge.p1.x, edge.p1.y, edge.p2.x, edge.p2.y);
      renderer.setColor(Color.SCARLET);
      renderer.end();
      renderer.begin(ShapeType.Filled);
      renderer.circle(edge.p1.x, edge.p1.y, 2);
      renderer.end();
    }
    renderer.begin(ShapeType.Filled);
    renderer.setColor(Color.ORANGE);
    for (int i = 0; i < roomsBuilder.corridors.size(); i++) {
      Edge edge = new Edge(roomsBuilder.corridors.get(i));
      edge.p1.x /= 10;
      edge.p1.y /= 10;
      edge.p2.x /= 10;
      edge.p2.y /= 10;
      renderer.rectLine(edge.p1, edge.p2, 10);
    }
    renderer.end();
    renderer.begin(ShapeType.Filled);
    renderer.setColor(Color.GREEN);
    for (Rectangle rect : roomsBuilder.mainRooms) { 
      Rectangle r = new Rectangle(rect);
      r.x /= 10;
      r.y /= 10;
      r.width /= 10;
      r.height /= 10;
      renderer.rect(r.x, r.y, r.width, r.height);
    }
    renderer.setColor(Color.GRAY);
    for (Rectangle rect : roomsBuilder.subRooms) {
      Rectangle r = new Rectangle(rect);
      r.x /= 10;
      r.y /= 10;
      r.width /= 10;
      r.height /= 10;
      renderer.rect(r.x, r.y, r.width, r.height);
    }
    renderer.end(); 
  }
  @Override
  public void render(SpriteBatch batch) {
    if (hideShapes) return;
    for (int i = 0; i < roomsBuilder.corridors.size(); i++) {
      Edge edge = new Edge(roomsBuilder.corridors.get(i));
      edge.p1.x /= 10;
      edge.p1.y /= 10;
      edge.p2.x /= 10;
      edge.p2.y /= 10;
      font.draw(batch, Integer.toString(i), edge.left().x, edge.bottom().y);
    }
  }
}
