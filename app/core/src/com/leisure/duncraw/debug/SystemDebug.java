package com.leisure.duncraw.debug;

import com.leisure.duncraw.screen.GameScreen;

import imgui.ImGui;
import lib.time.TimePeeker;
import lib.tooling.ToolAgent;

public class SystemDebug extends ToolAgent {
  public int fps = 0;
  public int frames = 0;
  public TimePeeker time = new TimePeeker();
  private final GameScreen game;
  float zoom[] = {0.8f};
  public SystemDebug(GameScreen game) {
    this.game = game;
    id = "System";
  }
  @Override
  public void tool() {
    frames += 1;
    if (time.sinceLastPeek() >= 1000) {
      time.peek();
      fps = frames;
      frames = 0;
    }
    ImGui.labelText("FPS", Integer.toString(fps));
    ImGui.sliderFloat("Zoom", zoom, 0.2f, 2f);
    game.camera.zoom = zoom[0];
  }
}
