package com.leisure.duncraw.debug;

import com.badlogic.gdx.Gdx;
import com.leisure.duncraw.art.chara.Chara;

import imgui.ImGui;
import lib.tooling.ToolAgent;

public class CharaDebug extends ToolAgent { 
  Chara chara;
  public CharaDebug(Chara inv) { 
    chara = inv; 
    id = String.format("Chara(%d)", chara.id);
    size.y = Gdx.graphics.getHeight()*0.4f;
  } 
  @Override
  public void tool() {
    if (ImGui.collapsingHeader("Attributes " + id)) {
      ImGui.labelText("Health", Integer.toString(chara.status.health));
      ImGui.labelText("Stamina", Integer.toString(chara.status.stamina));
      ImGui.labelText("Total Attack", Float.toString(chara.status.getAttack()));
      ImGui.labelText("Total Defense", Float.toString(chara.status.getDefense()));
      ImGui.labelText("Element Pow", Float.toString(chara.status.elementPower));
      ImGui.labelText("Bonus Attack", Float.toString(chara.status.bonusAttack));
      ImGui.labelText("Bonus Defense", Float.toString(chara.status.bonusDefense));
    }
    if (ImGui.collapsingHeader("Condition" + id)) {
      ImGui.labelText("World pos", String.format("%d %d", (int)chara.bounds.x, (int)chara.bounds.y));
      ImGui.labelText("Floor pos", String.format("%d %d", chara.mapAgent.x, chara.mapAgent.y));
    }

  }
}
