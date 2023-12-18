package com.leisure.duncraw.debug;

import imgui.ImGui;
import lib.time.TimePeeker;
import lib.tooling.ToolAgent;

public class SystemDebug extends ToolAgent {
  public int fps = 0;
  public int frames = 0;
  public TimePeeker time = new TimePeeker();
  public SystemDebug() {
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
  }
}
