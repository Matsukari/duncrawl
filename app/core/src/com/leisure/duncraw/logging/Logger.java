package com.leisure.duncraw.logging;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import lib.tooling.ToolAgent;

public class Logger extends ToolAgent {
  private static ArrayList<String> hidden = new ArrayList<>();
  public static final ArrayList<String> logList = new ArrayList<>();
  private static int streak = 0;
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
  public static void hide(String tag) {
    hidden.add(tag);
  }
  public static void show(String tag) {
    hidden.remove(tag);
  }
  private static String format(String tag, String msg) {
    return String.format("[%s] %s", tag, msg);
  }
  public static void log(String tag, String msg) {
    if (hidden.contains(tag)) return;
    if (!logList.isEmpty() && format(tag, msg).equals(logList.get(logList.size()-1))) streak++;
    else if (streak >= 3) {
      String str = String.format("...................and (%d) others", streak);
      logList.add(str);
      Gdx.app.log(tag, str);
      streak = 0;
    }
    else {
      logList.add(format(tag, msg));
      Gdx.app.log(tag, msg); 
    }
  }
  public static void error(String tag, String msg) { 
    if (hidden.contains(tag)) return;
    logList.add(String.format("ERROR: [%s] %s", tag, msg));
    Gdx.app.error(tag, msg); 
    System.exit(-1);
  }
  public static void error(Exception e) { 
    e.printStackTrace();
    System.exit(-1);
  }
}
