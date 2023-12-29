package com.leisure.duncraw.data;

import java.util.ArrayList;

public class ObjsData extends Dat {
  public ArrayList<ArrayList<String>> floorChests;
  public String magicDust;
  public String magicOre;
  public String bronzeMonsterCore;
  public String silverMonsterCore;
  public String goldMonsterCore;
  public String bronzeKey;
  public String silverKey;
  public String goldKey;
  public String ominousKey;
  public String shabbyDagger;
  public String assasinPride;
  public String sharpDagger;
  public String durandal;
  public String greenSteel;
  public String vampireFang;
  public String edibleFlesh;
  public String greenMeat;
  public String lowHealthPotion;
  public String lowStaminaPotion;
  public String midHealthPotion;
  public String midStaminaPotion;
  public String highStaminaPotion;
  public String highHealthPotion;
  public String expansionBag;
  public String lamp;
  public String torch;
  public ArrayList<String> rocks;
  public ArrayList<String> corpses;
  public ArrayList<String> totems;
  public ArrayList<String> flowers;

  @Override
  public void reset() {
    floorChests = new ArrayList<>();
    floorChests.add(new ArrayList<>());
    floorChests.get(0).add("dat/obj/chest.dat");
    magicDust = "dat/item/magic_dust.dat";
    magicOre = "dat/obj/magic_dust.dat";
    lamp = "dat/obj/lamp.dat";
    torch = "dat/obj/torch.dat";
    bronzeMonsterCore = "dat/item/monster_core_bronze.dat";
    silverMonsterCore = "dat/item/monster_core_silver.dat";
    goldMonsterCore = "dat/item/monster_core_gold/.dat";
    bronzeKey = "dat/item/key_bronze.dat";
    silverKey = "dat/item/key_silver.dat";
    goldKey = "dat/item/key_gold.dat";
    ominousKey = "dat/item/key_ominous.dat";
    shabbyDagger = "dat/equip/shabby_dagger.dat";
    assasinPride = "dat/equip/assasins_pride.dat";
    sharpDagger = "dat/equip/sharp_dagger.dat";
    durandal = "dat/equip/durandal.dat";
    greenSteel = "dat/equip/green_steel.dat";
    vampireFang = "dat/equip/vampire_fang.dat";
    edibleFlesh = "dat/item/edible_flesh.dat";
    greenMeat = "dat/item/green_meat.dat";
    lowHealthPotion = "dat/item/health_low.dat";
    lowStaminaPotion = "dat/item/stam_low.dat";
    midHealthPotion = "dat/item/health_mid.dat";
    midStaminaPotion = "dat/item/stam_mid.dat";
    highStaminaPotion = "dat/item/stam_high.dat";
    highHealthPotion = "dat/item/health_high.dat";
    expansionBag = "dat/item/expansion_bag.dat";
    rocks = new ArrayList<>();
    rocks.add("dat/obj/rock_1.dat");
    corpses = new ArrayList<>();
    corpses.add("dat/obj/corpse_1.dat");
    totems = new ArrayList<>();
    totems.add("dat/obj/totem_1.dat");
    flowers = new ArrayList<>();
    flowers.add("dat/obj/flower_1.dat");
  }
  
}
