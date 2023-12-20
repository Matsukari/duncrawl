package com.leisure.duncraw.data;

public class UiData extends Dat {
  public String dialogueBackground;
  public String health_frame;
  public String health_mask;
  @Override
  public void reset() {
    dialogueBackground = "images/ui/black_ellipsical_blur.png";
    health_mask = "images/ui/health_mask.png";
    health_frame = "images/ui/health_frame.png";
  }
  
}
