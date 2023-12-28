package com.leisure.duncraw.data;

import com.leisure.duncraw.art.chara.Status;

public class PlayerData extends Dat {
  public GeneralRank rank;
  public Inventory inventory;
  public Status status;
  public PlayerData(Status status, Inventory inventory) {
    this.status = status;
    this.inventory = inventory;
  }
  public PlayerData() {

  }
  @Override
  public void reset() {
    rank = GeneralRank.BRONZE;
    status = new Status();
    inventory = new Inventory();
    status.reset();
    inventory.reset();
  }
}
