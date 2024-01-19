package com.leisure.duncraw.screen;

import java.net.CookieHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.hud.Hud;
import com.leisure.duncraw.logging.Logger;

public class EndingScreen extends Screen {
  public Color bg = Color.valueOf("#bbbbbb");
  public Stage stage;
  public Table root;
  public EndingScreen() {
    stage = new Stage();
    root = new Table();
    root.setFillParent(true);
    Label label = Hud.createLabel("The game ends here.");
    label.setColor(Color.BLACK);
    root.add(label).center(); 
    stage.addActor(root);
    Gdx.input.setInputProcessor(stage);
  }
  @Override
  public void render(float delta) {
    ScreenUtils.clear(bg);
    stage.act();
    stage.draw();
  }
  
}
