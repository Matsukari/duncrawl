package com.leisure.duncraw.tooling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.FPSLogger;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImString;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;

public class Tooling {
  private FPSLogger fps;
  private ImGuiImplGlfw glfw;
  private ImGuiImplGl3 gl;
  private boolean start = true;
  private Array<ToolAgent> tools;
  private static Tooling tooling;

  public static void addAgent(ToolAgent tool) {
    assert tooling != null;
    tooling.tools.add(tool);
  }
  public static void removeAgent(ToolAgent tool) {
    assert tooling != null;
    tooling.tools.removeIndex(tooling.tools.indexOf(tool, false));
  }
  public static void init(Tooling v) {
    tooling = v;
    assert tooling != null;
  }
  public Tooling() {
    tools = new Array<ToolAgent>();
    glfw = new ImGuiImplGlfw();
    gl = new ImGuiImplGl3();
    long windowHandle = ((Lwjgl3Graphics)Gdx.graphics).getWindow().getWindowHandle();
    ImGui.createContext();
    ImGuiIO io = ImGui.getIO();
    io.setIniFilename(null);
    io.getFonts().addFontDefault();
    io.getFonts().build();
    glfw.init(windowHandle, true);
    gl.init("#version 150");
    ImGui.getStyle().setWindowMinSize(200, 200);
  }
  public void render() {
    glfw.newFrame();
    ImGui.newFrame();
   
    for (ToolAgent tool : tools) { 
      ImGui.setNextWindowPos(Gdx.graphics.getWidth()-200, 0);
      tool.tool();
    }
    ImGui.render();
    gl.renderDrawData(ImGui.getDrawData());

    if (ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse()) {

    }
  }
  public void dispose() {
    gl.dispose();
    glfw.dispose();
    ImGui.destroyContext();
 
  }
}
