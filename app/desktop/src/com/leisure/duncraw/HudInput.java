package com.leisure.duncraw;

import javax.swing.plaf.DesktopIconUI;

import com.badlogic.gdx.InputAdapter;
import com.leisure.duncraw.data.Settings.DesktopControls;
import com.leisure.duncraw.manager.HudManager;

public class HudInput extends InputAdapter {
  private final DesktopControls controls;
  private final HudManager hudManager;
  public HudInput(HudManager hudManager, DesktopControls controls) {
    this.hudManager = hudManager;
    this.controls = controls;

  }
  @Override
  public boolean keyDown(int keycode) {
    if (hudManager.inventoryHud.isVisible()) {
      if (keycode == controls.cancel || keycode == controls.inventory) return false;
      else if (keycode == controls.menuLeft) hudManager.inventoryHud.selectLeft();
      else if (keycode == controls.menuRight) hudManager.inventoryHud.selectRight();
      else if (keycode == controls.action) hudManager.inventoryHud.useSelected();
      return true;
    }
    // else if (hudManager.dialogueHud.isVisible()) {
    //   if (keycode == controls.action) hudManager.dialogueHud.next();
    // }
    return false;
  }
  
}
