package com.leisure.duncraw.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.Map;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import lib.tooling.ToolAgent;

public class Logger extends ToolAgent {
  public Logger() {
    size.y = Gdx.graphics.getHeight() - 100;
  }
  @Override
  public void tool() {
    if (ImGui.collapsingHeader("Logs", ImGuiTreeNodeFlags.DefaultOpen)) {
      ImGui.beginChild("Scroll panel");
      for (String log : logList) 
        ImGui.text(String.format("%s", log));
      ImGui.endChild();
    }
  }
  public static final ArrayList<String> logList = new ArrayList<>();
  public static void log(String tag, String msg) { 
    logList.add(String.format("[%s] %s", tag, msg));
    Gdx.app.log(tag, msg); 
  }
}
