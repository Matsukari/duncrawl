package com.leisure.duncraw;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.FontsData;
import com.leisure.duncraw.data.UiData;

import lib.animation.LinearAnimation;

public class Graphics {
  public static AssetManager assets;
  public static FontsData fontSources;
  public static BitmapFont defFont;
  public static HashMap<String, LinearAnimation<TextureRegion>> anims = new HashMap<>();
  public static TextureRegion getTextureRegion(String file) { return new TextureRegion(assets.get(file, Texture.class)); }
  public static TextureRegion getSafeTextureRegion(String file) {
    Texture texture = Graphics.assets.get(file, Texture.class, false);
    if (texture == null) {
      Graphics.assets.load(file, Texture.class);
      Graphics.assets.finishLoadingAsset(file);
    }
    return getTextureRegion(file);

  } 
  public static BitmapFont getFont(String source) { return assets.get(source, BitmapFont.class); }
  public static void init () {
    assets = new AssetManager();
    fontSources = AssetSource.getFontsData();
    assets.load(fontSources.def, BitmapFont.class);
    assets.load(fontSources.normal, BitmapFont.class);
    assets.load(fontSources.important, BitmapFont.class);
    assets.load(fontSources.reading, BitmapFont.class);
    assets.finishLoading();

    UiData uiSources = AssetSource.getUiData();
    assets.load(uiSources.dialogueBackground, Texture.class);
    assets.load(uiSources.health_mask, Texture.class);
    assets.load(uiSources.health_frame, Texture.class);
    assets.finishLoading();
  }
  public static void dispose() {
    if (defFont != null) defFont.dispose();
    assets.dispose();
  }
}
