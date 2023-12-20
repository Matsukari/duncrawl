package com.leisure.duncraw.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.leisure.duncraw.art.chara.Status;
import com.leisure.duncraw.helper.Instantiator;
import com.leisure.duncraw.logging.Logger;

public class AssetSource {
  public transient final FileHandle ini;
  public String save;
  public String floors;
  public String charas;
  public String objects;
  public String fonts;
  public String music;
  public String ui;
  public static AssetSource instance;
  public AssetSource(FileHandle ini) {
    this.ini = ini; 
  }
  public AssetSource() { ini = null; }
  public static SaveData getSaveData() { 
    FileHandle saveFile = Gdx.files.local(instance.save);
    SaveData save = new SaveData(new Status(), new Inventory()); 
    try { save = Deserializer.load(SaveData.class, saveFile); }
    catch (Exception e) { e.printStackTrace(); }
    // This new SaveData is constructed defaultly, so set it right
    Serializer.save(save, saveFile);
    return save; 
  }
  public static void save() { Serializer.save(instance, instance.ini); }
  public static AssetSource load() throws Exception { return Deserializer.load(AssetSource.class, instance.ini); }
  public static FloorsData getFloorsData() 
  { return getGenericData(FloorsData.class, instance.floors, ()->{FloorsData dat = new FloorsData(); dat.reset(); return dat;}); }
  public static CharasData getCharasData() 
  { return getGenericData(CharasData.class, instance.charas, ()->{CharasData dat = new CharasData(); dat.reset(); return dat;}); }
  public static FontsData getFontsData() 
  { return getGenericData(FontsData.class, instance.fonts, ()->{FontsData dat = new FontsData(); dat.reset(); return dat;}); }
  public static MusicData getMusicData() 
  { return getGenericData(MusicData.class, instance.music, ()->{MusicData dat = new MusicData(); dat.reset(); return dat;}); }
  public static UiData getUiData() 
  { return getGenericData(UiData.class, instance.ui, ()->{UiData dat = new UiData(); dat.reset(); return dat;}); }

  public static <T> T getGenericData(Class<T> tClass, String local, Instantiator<T> instanciator) {
    T dat;
    try { dat = Deserializer.load(tClass, Gdx.files.local(local)); }
    catch (Exception e) { 
      e.printStackTrace(); 
      dat = instanciator.instance();
      Serializer.save(dat, Gdx.files.local(local));
    }
    return dat;
  }
  public static void init(FileHandle ini) {
    instance = new AssetSource(ini);
    try { instance = AssetSource.load(); }
    catch (Exception e) {
      e.printStackTrace();
      Logger.log("AssetSource", "Cannot parse ini file, so it was defaulted");
      instance.save = "save/dungeon_crawler.dat";
      instance.floors = "dat/floors.dat";
      instance.objects = "dat/objects.dat";
      instance.charas = "dat/charas.dat";
      instance.fonts = "dat/fonts.dat";
      instance.music = "dat/music.dat";
      instance.ui = "dat/ui.dat";
      AssetSource.save();
    }
  }
}
