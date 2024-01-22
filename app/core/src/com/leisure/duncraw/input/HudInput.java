package com.leisure.duncraw.input;

import javax.swing.plaf.DesktopIconUI;

import com.badlogic.gdx.InputAdapter;
import com.leisure.duncraw.art.chara.states.TalkState;
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
    if (hudManager.windowUi.isVisible() && hudManager.windowUi.currentIndex == 0) {
      if (keycode == controls.cancel || keycode == controls.inventory) return false;
      else if (keycode == controls.menuLeft) hudManager.windowUi.statusWindow.inventoryHud.selectLeft();
      else if (keycode == controls.menuRight) hudManager.windowUi.statusWindow.inventoryHud.selectRight();
      else if (keycode == controls.action) hudManager.windowUi.statusWindow.inventoryHud.useSelected();
      return true;
    }
    else if (hudManager.dialogueHud.isVisible() && !(hudManager.windowUi.statusWindow.statusHud.player.state instanceof TalkState) ){
      if (keycode == controls.action && !hudManager.dialogueHud.next()) {
        hudManager.statusHud.player.lockState = false;
        // hudManager.dialogueHud.restart();
      }
      return true;
    }
    return false;
  }
  
}
