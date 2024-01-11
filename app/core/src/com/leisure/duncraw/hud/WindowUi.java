package com.leisure.duncraw.hud;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.StringBuilder;
import com.leisure.duncraw.logging.Logger;

public class WindowUi extends Hud {
  public ArrayList<Tab> tabs;
  public StatusWindow statusWindow;
  public ProgressionWindow progressionWindow;
  public SettingsWindow settingsWindow;
  public Color containerBgColor;
  public Color containerOutlineBgColor;
  public Color panelBgColor;
  protected Stack tabPanel;
  public Tab currentTab;
  public WindowUi(StatusHud statusHud, InventoryHud inventoryHud, MapFull mapHud, QuestFull quest) {
    tabs = new ArrayList<>();
    statusWindow = new StatusWindow(statusHud, inventoryHud);
    progressionWindow = new ProgressionWindow(mapHud, quest);
    settingsWindow = new SettingsWindow();
    containerBgColor = new Color(0.06f, 0.06f, 0.06f, 1f);
    panelBgColor = new Color(0.1f, 0.1f, 0.1f, 1f);
    containerOutlineBgColor = new Color(0.3f, 0.3f, 0.3f, 1f);
  }
  @Override
  protected void onInit() {
    tabs.add(new Tab(createLabel("Status"), statusWindow));
    tabs.add(new Tab(createLabel("Progression"), progressionWindow));
    tabs.add(new Tab(createLabel("Settings"), settingsWindow));
   
    tabPanel = new Stack();
    Table tabRow = new Table();
    for (Tab tab : tabs) {
      tab.content.init(stage, data, shapeRenderer, batch);
      tabRow.add(tab.title).center().top().expand().fill();
      tabPanel.addActor(tab.content);
      tab.title.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          Logger.log("WindowUi", "Tab click") ;
          switchTab(tab.title.getText());
        }
      });
    }
    add(tabRow).top().expandX().fillX().height(60).padLeft(10);
    row(); add(tabPanel).fill().expand().center();
    center();
    switchTab(1);
    setVisible(false);  
  }
  @Override
  public void drawShapes() {
    if (!isVisible()) return;
    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(containerBgColor);
    shapeRenderer.rect(getGlobalX(), getGlobalY(), getWidth(), getHeight());
    shapeRenderer.setColor(panelBgColor);
    shapeRenderer.rect(getGlobalX() + tabPanel.getX(), getGlobalY() - tabPanel.getY(), tabPanel.getWidth(), tabPanel.getHeight());
    shapeRenderer.end();
    shapeRenderer.begin(ShapeType.Line);
    shapeRenderer.setColor(containerOutlineBgColor);
    shapeRenderer.rect(getGlobalX(), getGlobalY(), getWidth(), getHeight());
    shapeRenderer.end();
    if (currentTab.content.isVisible()) currentTab.content.drawShapes();
  }
  @Override
  public void update() {
    currentTab.content.update();
  }
  public void switchTab(int index) {
    for (Tab tab : tabs) tab.content.setVisible(false);
    currentTab = tabs.get(index);
    currentTab.content.setVisible(true);
  }
  public void switchTab(StringBuilder title) {
    for (Tab tab : tabs) {
      tab.content.setVisible(false);
      if (tab.title.getText().equalsIgnoreCase(title)) currentTab = tab;
    }
    currentTab.content.setVisible(true);
  }

}
