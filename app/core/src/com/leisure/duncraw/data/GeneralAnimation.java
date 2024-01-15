package com.leisure.duncraw.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Array;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

public class GeneralAnimation {
  public static LinearAnimation<TextureRegion> line(String source, PlayMode mode, int size) {
    Texture texture = Graphics.getSafeTextureRegion(source).getTexture();
    Array<TextureRegion> frames = new Array<TextureRegion>(TextureRegion.split(Graphics.assets.get(source), size, size)[0]);
    // Logger.log("GeneralAnimation", "Got " + Integer.toString(frames.size));
    return new LinearAnimation<TextureRegion>(0.1f, frames, mode);
  } 
  public static LinearAnimation<TextureRegion> line(String source, PlayMode mode) { 
    Texture texture = Graphics.getSafeTextureRegion(source).getTexture();
    Array<TextureRegion> frames = new Array<TextureRegion>(TextureRegion.split(Graphics.assets.get(source), texture.getHeight(), texture.getHeight())[0]);
    // Logger.log("GeneralAnimation", "Got " + Integer.toString(frames.size));
    return new LinearAnimation<TextureRegion>(0.1f, frames, mode);
  }
  public static LinearAnimation<TextureRegion> line(String source) { return line(source, PlayMode.LOOP); }
}
