package com.leisure.duncraw.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Array;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

public class GeneralAnimation {
  public static LinearAnimation<TextureRegion> line(String source, PlayMode mode) {
    Logger.log("GeneralAnimation", "Creating animation for: " + source);
    return new LinearAnimation<TextureRegion>(0.1f, 
      new Array<TextureRegion>(TextureRegion.split(new Texture(Gdx.files.local(source)), 16, 16)[0]), mode);
  } 
  public static LinearAnimation<TextureRegion> line(String source) { return line(source, PlayMode.LOOP); }
}
