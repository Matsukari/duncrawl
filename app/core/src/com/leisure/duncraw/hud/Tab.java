package com.leisure.duncraw.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Tab extends Hud {
  public Hud content;
  public Label title;
  public Tab(Label title, Hud content) {
    this.title = title;
    this.content = content;
    add(title).top();
    row();
    add(content).top();
  }
  @Override
  public void drawShapes() {
    content.drawShapes();
  }
  @Override
  public void update() {
    content.update();
  }
}
