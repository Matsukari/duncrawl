package com.leisure.duncraw.art.item.items;

import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.logging.Logger;

public class Document extends Item {
  public Document(String datFile) {
    super(datFile);
  }
  @Override
  public void onUse() {
    // owner.status.setHealth(owner.status.health + 10); 
    Logger.log("Document", "Used item");
  }
}

