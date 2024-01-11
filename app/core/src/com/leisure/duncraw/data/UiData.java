package com.leisure.duncraw.data;

import com.badlogic.gdx.graphics.Color;

public class UiData extends Dat {
  public String dialogueBackground;
  public String health_frame;
  public String health_mask;
  // public Color healthColor;
  // public Color staminaColor;
  
  @Override
  public void reset() {
    dialogueBackground = "images/ui/black_ellipsical_blur.png";
    health_mask = "images/ui/health_mask.png";
    health_frame = "images/ui/health_frame.png";
  }
  
}
