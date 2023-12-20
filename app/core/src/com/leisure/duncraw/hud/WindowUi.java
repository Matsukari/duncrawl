package com.leisure.duncraw.hud;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.StringBuilder;
import com.leisure.duncraw.Graphics;

public class WindowUi extends Hud {
  public ArrayList<Tab> tabs;
  public StatusWindow statusWindow;
  public ProgressionWindow progressionWindow;
  public SettingsWindow settingsWindow;
  public Color containerBgColor;
  public Tab currentTab;
  public WindowUi(StatusHud statusHud, InventoryHud inventoryHud, MapHud mapHud) {
    tabs = new ArrayList<>();
    statusWindow = new StatusWindow(statusHud, inventoryHud);
    progressionWindow = new ProgressionWindow(mapHud);
    settingsWindow = new SettingsWindow();
    containerBgColor = new Color(0.1f, 0.1f, 0.1f, 1f);
  }
  @Override
  protected void onInit() {
    tabs.add(new Tab(createLabel("Status"), statusWindow));
    tabs.add(new Tab(createLabel("Progression"), progressionWindow));
    tabs.add(new Tab(createLabel("Settings"), settingsWindow));
    for (Tab tab : tabs) {
      tab.content.init(stage, data, shapeRenderer);
      add(tab).left().top().expand();
      tab.title.addListener(new ClickListener() { 
        @Override public void clicked(InputEvent event, float x, float y) {
        switchTab(tab.title.getText());
        }
      });
    }
    switchTab(0);
    setVisible(false);  
  }
  @Override
  public void drawShapes() {
    if (!isVisible()) return;
    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(containerBgColor);
    shapeRenderer.rect(getX(), getTop()-400, getWidth(), 400);
    shapeRenderer.end();
    if (currentTab.isVisible()) currentTab.drawShapes();
  }
  @Override
  public void update() {
    currentTab.update();
  }
  public void switchTab(int index) {
    for (Tab tab : tabs) tab.content.setVisible(false);
    currentTab = tabs.get(index);
    currentTab.setVisible(true);
  }
  public void switchTab(StringBuilder title) {
    for (Tab tab : tabs) if (tab.title.getText().equalsIgnoreCase(title)) currentTab = tab;
  }

}
