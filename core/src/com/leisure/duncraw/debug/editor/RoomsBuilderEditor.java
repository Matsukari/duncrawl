package com.leisure.duncraw.debug.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.map.generator.RoomsBuilder;

import imgui.ImGui;
import imgui.type.ImInt;
import lib.math.Edge;
import lib.tooling.ToolAgent;

public class RoomsBuilderEditor extends ToolAgent {
  public ImInt points = new ImInt(3);
  public RoomsBuilder roomsBuilder;
  private ShapeRenderer renderer;
  public RoomsBuilderEditor(RoomsBuilder roomsBuilder, ShapeRenderer renderer) {
    super("RoomsBuilderEditor");
    this.roomsBuilder = roomsBuilder;
    this.renderer = renderer;
    renderer.scale(0.1f, 0.1f, 1f);
  }
  @Override
  public void tool() {
    ImGui.inputInt("Rooms", points);
    if (ImGui.button("Generate")) roomsBuilder.build(points.get(), new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), new Vector2(0.2f, 1f), new Vector2(0.2f, 1f));
    for (Edge e : roomsBuilder.nodes) {
      renderer.begin(ShapeType.Line);
      renderer.setColor(Color.DARK_GRAY);
      renderer.line(e.p1.x, e.p1.y, e.p2.x, e.p2.y);
      renderer.setColor(Color.SCARLET);
      renderer.end();
      renderer.begin(ShapeType.Filled);
      renderer.circle(e.p1.x, e.p1.y, 2);
      renderer.end();
    }
    renderer.begin(ShapeType.Line);
    renderer.setColor(Color.ORANGE);
    for (Edge line : roomsBuilder.corridors) {
      renderer.line(line.p1, line.p2);
    }
    renderer.setColor(Color.WHITE);
    for (Rectangle rect : roomsBuilder.rooms) {
      renderer.rect(rect.x, rect.y, rect.width, rect.height);
    }
    renderer.setColor(Color.GREEN);
    for (Rectangle rect : roomsBuilder.mainRooms) {
      renderer.rect(rect.x, rect.y, rect.width, rect.height);
    }
    renderer.end(); 
  }
}
