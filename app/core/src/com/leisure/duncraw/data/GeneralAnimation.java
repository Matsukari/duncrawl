package com.leisure.duncraw.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Array;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

public class GeneralAnimation {
  public static LinearAnimation<TextureRegion> line(String source, PlayMode mode, int size) {
    Logger.log("GeneralAnimation", "Creating animation for: " + source);
    Texture texture = Graphics.assets.get(source, Texture.class, false);
    if (texture == null) {
      Graphics.assets.load(source, Texture.class);
      Graphics.assets.finishLoadingAsset(source);
    }
    return new LinearAnimation<TextureRegion>(0.1f, 
      new Array<TextureRegion>(TextureRegion.split(Graphics.assets.get(source), size, size)[0]), mode);
  } 
  public static LinearAnimation<TextureRegion> line(String source, PlayMode mode) { return line(source, mode, 16); }
  public static LinearAnimation<TextureRegion> line(String source) { return line(source, PlayMode.LOOP); }
}