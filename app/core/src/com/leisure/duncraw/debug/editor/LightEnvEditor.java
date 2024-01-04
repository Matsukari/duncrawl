package com.leisure.duncraw.debug.editor;

import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.art.lighting.Lighting;
import com.leisure.duncraw.art.lighting.PointLight;

import imgui.ImGui;
import lib.tooling.ToolAgent;

public class LightEnvEditor extends ToolAgent {
  public Lighting lighting;
  private float modulate[] = { 0.033f, 0.033f, 0.100f, 0.2f };
  public LightEnvEditor(Lighting environment) {
    this.lighting = environment;
    this.id = "LightEnvEditor";
  }
  @Override
  public void tool() {
    ImGui.sliderFloat4("Modulate", modulate, 0, 1);
    lighting.getEnv().envColor.set(modulate[0], modulate[1], modulate[2], modulate[3]);
  }
  
}
