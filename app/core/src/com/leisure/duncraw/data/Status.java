package com.leisure.duncraw.data;

public class Status {
  public GeneralRank rank;
  public Inventory inventory;
  public com.leisure.duncraw.art.chara.Status status;
  public Status(com.leisure.duncraw.art.chara.Status status, Inventory inventory) {
    this.status = status;
    this.inventory = inventory;
  }
  public Status() {}
  public void init() {
    rank = GeneralRank.BRONZE;
    status = new com.leisure.duncraw.art.chara.Status();
    inventory = new Inventory();
    status.reset();
    inventory.reset();
  }
}
