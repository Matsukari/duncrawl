package com.leisure.duncraw.manager;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.Audio;
import com.leisure.duncraw.data.MusicData;
import com.leisure.duncraw.screen.GameScreen;

public class MusicManager {
  private final GameScreen game;
  public final MusicData sources;
  public float volume;
  public MusicManager(GameScreen game, MusicData data, float volume) {
    this.game = game;
    this.sources = data;
    this.volume = volume;
    String source = getSource(sources.earlyGame);
    Audio.getManager().load(source, Music.class);
    Audio.getManager().finishLoading();
    Audio.applyMusicVolume(volume, data); 
    Music music = Audio.getMusic(source);
    music.setLooping(true);
    music.play();
  }
  public String getSource(ArrayList<String> choices) {
    return choices.get(MathUtils.random(choices.size()-1));
  }
  public void update() {
     
  }
}
