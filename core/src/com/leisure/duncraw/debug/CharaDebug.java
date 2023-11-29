package com.leisure.duncraw.debug;

import com.badlogic.gdx.Gdx;
import com.leisure.duncraw.art.chara.Chara;

import imgui.ImGui;
import lib.tooling.ToolAgent;

public class CharaDebug extends ToolAgent { 
  Chara chara;
  public CharaDebug(Chara inv) { 
    chara = inv; 
    id = "Enemy";
    size.y = Gdx.graphics.getHeight()*0.4f;
  } 
  @Override
  public void tool() {
    ImGui.labelText("Health", Integer.toString(chara.status.health));
  }
}
