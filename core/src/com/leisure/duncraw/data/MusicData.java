package com.leisure.duncraw.data;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class MusicData extends Dat {
  public ArrayList<String> earlyGame;
  public ArrayList<String> midGame;
  public ArrayList<String> lateGame;
  public String finalBoss;
  public String midBoss;
  public String earlyBoss;
  public String safeArea;
  public String endingScene;
  public String menuScreen;

  public static MusicData fromDat(String source) {
    MusicData data = new MusicData();
    data.reset();
    try { data = Deserializer.load(MusicData.class, Gdx.files.local(source)); } 
    catch (Exception e) { Serializer.save(data, Gdx.files.local(source)); }
    return data;
  } 
  @Override
  public void reset() {
    finalBoss = "audio/music/final_boss.ogg";
    midBoss = "audio/music/mid_boss.ogg";
    earlyBoss = "audio/music/earlyBoss.ogg";
    safeArea = "audio/music/safeArea.ogg";
    endingScene = "audio/music/ending_scene.ogg";
    menuScreen = "audio/music/menu_screen.ogg";
    earlyGame = new ArrayList<>();
    midGame = new ArrayList<>();
    lateGame = new ArrayList<>();
    earlyGame.add("audio/music/early_game_1.ogg");
    midGame.add("audio/music/mid_game_1.ogg");
    lateGame.add("audio/music/late_game_1.ogg");
  }
}
