package com.leisure.duncraw.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Tab {
  public Hud content;
  public Label title;
  public Tab(Label title, Hud content) {
    this.title = title;
    this.content = content;
  }
}
