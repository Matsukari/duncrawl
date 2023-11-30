package com.leisure.duncraw;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lib.animation.LinearAnimation;

public class Graphics {
  public static AssetManager assets;
  public static BitmapFont defFont;
  public static HashMap<String, LinearAnimation<TextureRegion>> anims = new HashMap<>();
  public static TextureRegion getTextureRegion(String file) { return new TextureRegion(assets.get(file, Texture.class)); }
  public static void init () {
    assets = new AssetManager();
  }
}
