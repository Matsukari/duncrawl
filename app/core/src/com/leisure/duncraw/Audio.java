package com.leisure.duncraw;

import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.CharasData;
import com.leisure.duncraw.data.MusicData;
import com.leisure.duncraw.logging.Logger;

public class Audio {
  private static AssetManager assets;
  public static Sound getSound(String source) { return assets.get(source, Sound.class, false); }
  public static Music getMusic(String source) { return assets.get(source, Music.class, false); }
  public static void init() {
    assets = new AssetManager();
    CharasData charas = AssetSource.getCharasData();
    loadChara(charas.player);
    loadChara(charas.ghost);
    assets.finishLoading();
  }
  public static AssetManager getManager() { return assets; }
  private static void loadChara(String chara) {
    for (Map.Entry<String, String> entry : CharaData.fromDat(chara).sounds.entrySet()) {
      Logger.log("Audio", "Loading chara's " + entry.getKey() + " : " + entry.getValue());
      assets.load((String)entry.getValue(), Sound.class);
    }
  }
  public static void applyMusicVolume(float volume, MusicData data) {
    try {
      for (String m : data.earlyGame) assets.get(m, Music.class).setVolume(volume/3);
      for (String m : data.midGame) assets.get(m, Music.class).setVolume(volume/3);
    } catch (Exception e) { }
  }
  public static void dispose() {
    assets.dispose();
  }
}
