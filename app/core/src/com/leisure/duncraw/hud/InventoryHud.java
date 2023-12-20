package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.data.Inventory;

public class InventoryHud extends Hud {
  protected Label titleLabel;
  protected Inventory inventory;
  protected SpriteBatch batch;
  public float itemSize;
  public int cols;
  public int itemPadding;
  public Color containerBgColor;
  public Color itemBgColor;
  public InventoryHud(Inventory inventory, SpriteBatch batch) {
    this.inventory = inventory;
    this.batch = batch;
    titleLabel = createLabel("Inventory");
    itemBgColor = new Color(0f, 0f, 0f, 1);
    containerBgColor = new Color(0.1f, 0.1f, 0.1f, 1f);
    cols = 5;
    itemSize = 32;
    itemPadding = 5;
    add(titleLabel).center().top().expand();
    setVisible(false);  
  }
  @Override
  public void drawShapes() {
    if (!isVisible()) return;
    // Shape and sprites don't render together
    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(containerBgColor);
    shapeRenderer.rect(getX(), getTop()-400, getWidth(), 400);
    shapeRenderer.setColor(itemBgColor);
    for (int i = 0; i < inventory.capacity; i++) {
      float x = getX()+ ((i%cols) * (itemSize + itemPadding + 5)) + itemPadding;
      float y = getTop() - itemSize - itemPadding - ((i/cols) * itemSize);
      shapeRenderer.circle(x+(itemSize/2), y+(itemSize/2), itemSize/2+itemPadding);
    }
    shapeRenderer.end();
    batch.begin();
    for (int i = 0; i < inventory.data.size(); i++) {
      Item item = inventory.data.get(i); 
      float x = getX()+ ((i%cols) * (itemSize + itemPadding + 5)) * itemPadding;
      float y = getTop() - itemSize - itemPadding - ((i/cols) * itemSize);
      item.renderStore(batch, x, y, itemSize, itemSize);
    }
    batch.end();
  }
}
