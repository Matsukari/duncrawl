package com.leisure.duncraw.debug;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import imgui.ImGui;
import lib.tooling.ToolAgent;

public class SpriteBatchDebug extends ToolAgent {
  private ArrayList<SpriteBatch> batches = new ArrayList<>();
  public SpriteBatchDebug(SpriteBatch... batches) {
    for (SpriteBatch b : batches) {
      this.batches.add(b);
    }
    id = "SpriteBatch";
  }
  @Override
  public void tool() {
    for (int i = 0; i < batches.size(); i++) {
      SpriteBatch batch = batches.get(i);
      if (ImGui.collapsingHeader("Batch " + Integer.toString(i))) {
        ImGui.labelText("Render calls", Integer.toString(batch.renderCalls));
        ImGui.labelText("Max rendered", Integer.toString(batch.maxSpritesInBatch));
      }
    }
  }
  
}
