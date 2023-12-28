package lib.tooling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import com.badlogic.gdx.utils.Array;
import com.leisure.duncraw.logging.Logger;

public class Tooling extends InputAdapter {
  private ImGuiImplGlfw glfw;
  private ImGuiImplGl3 gl;
  private Array<ToolAgent> tools;
  private static Tooling tooling;
  private ShapeRenderer renderer;
  private SpriteBatch batch;
  private boolean isFocused;
  
  @Override public boolean mouseMoved(int screenX, int screenY) { return isFocused; }
  @Override public boolean keyDown(int keycode) { return isFocused; }
  @Override public boolean keyUp(int keycode) { return isFocused; }
  @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return isFocused; }
  @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return isFocused; } 
  @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return isFocused; }

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
    renderer = new ShapeRenderer();
    batch = new SpriteBatch();
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
        tools.get(i).render(renderer);
        batch.begin();
        tools.get(i).render(batch);
        batch.end();
      }
    }
    ImGui.end();
    ImGui.render();
    gl.renderDrawData(ImGui.getDrawData());

    if (ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse()) {
      isFocused = true;
      // Logger.log("Tooling", "Capture"); 
    }
    else isFocused = false;
  }
  public void dispose() {
    gl.dispose();
    glfw.dispose();
    ImGui.destroyContext();
 
  }
}
