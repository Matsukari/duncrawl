package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.data.Inventory;
import com.leisure.duncraw.helper.IdGenerator;
import com.leisure.duncraw.logging.Logger;

public class InventoryHud extends Hud {
  protected Label titleLabel;
  protected Inventory inventory;
  protected SpriteBatch batch;
  public float itemSize;
  public int cols;
  public int itemPadding;
  public Color containerBgColor;
  public Color itemBgColor;
  public BitmapFont quantityLabelFont;
  public InventoryHud(Inventory inventory, SpriteBatch batch) {
    this.inventory = inventory;
    this.batch = batch;
    titleLabel = createLabel("Inventory");
    itemBgColor = new Color(0f, 0f, 0f, 1);
    containerBgColor = new Color(0.1f, 0.1f, 0.1f, 1f);
    quantityLabelFont = Graphics.getFont(Graphics.fontSources.def);
    cols = 5;
    itemSize = 32;
    itemPadding = 5;
    add(titleLabel).center().top().expand();
    setVisible(false);  
    id = IdGenerator.gen();
  }
  public int id;
  @Override
  public void drawShapes() {
    if (!isVisible()) return;
    // Shape and sprites don't render together
    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(containerBgColor);
    shapeRenderer.rect(getGlobalX(), getGlobalY(), getWidth(), getHeight());
    shapeRenderer.setColor(itemBgColor);
    for (int i = 0; i < inventory.capacity; i++) {
      float x = getGlobalX()+ ((i%cols) * (itemSize + itemPadding + 5)) + itemPadding;
      float y = getGlobalY() + getHeight() - itemSize - itemPadding - ((i/cols) * itemSize);
      shapeRenderer.circle(x+(itemSize/2), y+(itemSize/2), itemSize/2+itemPadding);
      
    }
    shapeRenderer.end();
    batch.begin();
    for (int i = 0; i < inventory.items.size(); i++) {
      Item item = inventory.items.get(i); 
      float x = getGlobalX()+ ((i%cols) * (itemSize + itemPadding + 5)) * itemPadding;
      float y = getGlobalY() + getHeight() - itemSize - itemPadding - ((i/cols) * itemSize);
      item.renderStore(batch, x, y, itemSize, itemSize);
      quantityLabelFont.draw(batch, String.format("%d/%d", item.quantity, item.maxQuantity), x, y);
    }
    batch.end();
  }
}
