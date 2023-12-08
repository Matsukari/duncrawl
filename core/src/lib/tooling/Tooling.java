package lib.tooling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.math.Vector2;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import com.badlogic.gdx.utils.Array;

public class Tooling {
  private ImGuiImplGlfw glfw;
  private ImGuiImplGl3 gl;
  private Array<ToolAgent> tools;
  private Array<Vector2> toolsPos;
  private static Tooling tooling;
  private Vector2 lastDock = new Vector2(Gdx.graphics.getWidth(), 0);

  public static void addAgent(ToolAgent tool, boolean next, boolean below) {
    assert tooling != null;
    tooling.tools.add(tool);
    if (next) {
      tooling.lastDock.x -= tool.size.x;
      tooling.toolsPos.add(new Vector2(tooling.lastDock.x, tooling.lastDock.y));
    }
    else if (below) {
      tooling.toolsPos.add(new Vector2(tooling.lastDock.x, tooling.lastDock.y));
      tooling.lastDock.y += tool.size.y;
    }
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
    toolsPos = new Array<>();
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
    ImGui.getStyle().setWindowMinSize(200, Gdx.graphics.getHeight());
  }
  public void render() {
    glfw.newFrame();
    ImGui.newFrame();
   
    ImGui.begin("Tools");
    // ImGui.setNextWindowSize(200, 700);
    // ImGui.setNextWindowPos(Gdx.graphics.getWidth()-200, 0);
    for (int i = 0; i < tools.size; i++) { 
      // ImGui.setNextWindowPos(toolsPos.get(i).x, toolsPos.get(i).y);
      // ImGui.setNextWindowSize(tools.get(i).size.x, tools.get(i).size.y);
      ImGui.separator();
      ImGui.text(tools.get(i).id);
      tools.get(i).tool();
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
