package com.leisure.duncraw;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.GraphicsData;

import lib.animation.LinearAnimation;

public class Graphics {
  public static AssetManager assets;
  public static BitmapFont defFont;
  public static LinearAnimation<TextureRegion> dropAnim;
  public static void init () {
    assets = new AssetManager();
    GraphicsData dat = AssetSource.getGraphicsData();
    dropAnim = GeneralAnimation.line(dat.dropAnimation);
  }
}
