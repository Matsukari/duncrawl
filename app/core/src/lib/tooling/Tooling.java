package lib.tooling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.math.Vector2;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import com.badlogic.gdx.utils.Array;

public class Tooling {
  private ImGuiImplGlfw glfw;
  private ImGuiImplGl3 gl;
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
    ImGui.getStyle().setWindowMinSize(200, Gdx.graphics.getHeight()-100);
  }
  public void render() {
    glfw.newFrame();
    ImGui.newFrame();
   
    ImGui.begin("Tools");
    for (int i = 0; i < tools.size; i++) { 
      ImGui.separator();
      if (ImGui.collapsingHeader(tools.get(i).id, ImGuiTreeNodeFlags.DefaultOpen)) {
        tools.get(i).tool();
      }
    }
    ImGui.end();
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