package com.leisure.duncraw;

import java.sql.BatchUpdateException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leisure.duncraw.art.AnimatedArt;
import com.leisure.duncraw.art.gfx.GfxAnimation;
import com.leisure.duncraw.art.gfx.GfxInterpolation;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.screen.Screen;
import com.leisure.duncraw.screen.SplashScreen;

import lib.time.Timer;

public class SplashScreenInput extends SplashScreen implements InputProcessor {
  private GfxAnimation icon;
  private SpriteBatch batch = new SpriteBatch();
  public SplashScreenInput() {
    icon = new GfxAnimation(GeneralAnimation.line("images/ui/splash_icon.png", PlayMode.NORMAL, 64), false);
    icon.bounds.setSize(64);
    icon.bounds.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    icon.start();
  }  
  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.BLACK);
    batch.begin();
    icon.render(batch);
    batch.end();
    if (icon.isFinished()) change();
  }
  @Override
  public void dispose() {
    batch.dispose();
  }
  @Override
  public Screen next() {
    // return new MenuScreenInput();
    return new GameScreenInput(AssetSource.getSaveData());
  }
  @Override
  public boolean keyDown(int keycode) {
    return true;
  }
  @Override
  public boolean keyUp(int keycode) {
    return true;
  }
  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }
  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }
  @Override
  public boolean keyTyped(char character) {
    return false;
  }
  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }
  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }
  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }
  @Override
  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    return false;
  }
}

