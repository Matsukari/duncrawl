package com.leisure.duncraw.hud;

import org.jgrapht.alg.linkprediction.SaltonIndexLinkPrediction;
import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.data.Inventory;
import com.leisure.duncraw.helper.IdGenerator;
import com.leisure.duncraw.helper.SString;
import com.leisure.duncraw.logging.Logger;

import lib.math.Pointi;

public class InventoryHud extends Hud {
  protected Label titleLabel;
  protected Inventory inventory;
  public float itemSize;
  public int cols;
  public int itemPadding;
  public Pointi itemMargin;
  public Color containerBgColor = Color.valueOf("#1a1a1a");
  public Color itemBgColor = Color.valueOf("#101010");
  public BitmapFont quantityLabelFont;
  public int id;
  public int currIndex = 0;
  public Item currItem;

  public Label itemLabel;
  public Label itemDescLabel;


  public InventoryHud(Inventory inventory, SpriteBatch batch) {
    this.inventory = inventory;
    this.batch = batch;
    titleLabel = createLabel("Inventory");
    quantityLabelFont = Graphics.getFont(Graphics.fontSources.def);
    cols = 5;
    itemSize = 32;
    itemPadding = 5;
    itemMargin = new Pointi(15, 25);
    add(titleLabel).center().top().expand().padTop(10);
    setVisible(false);  
    selectLeft();
    selectRight();
    
    itemLabel = createLabel("");
    itemDescLabel = createLabel("");
    row();
    add(itemLabel).center().top().padTop(20);
    row();
    add(itemDescLabel).center().top().padTop(30);
    id = IdGenerator.gen();
  }
  public void selectLeft() {
    currItem = inventory.select(--currIndex);
  }
  public void selectRight() {
    currItem = inventory.select(++currIndex);
  }
  public void useSelected() {
    if (currItem != null) {
      Logger.log("InventoryHud", "Used item");
      currItem = inventory.use(currItem);
    }
    else Logger.log("InventoryHud", "Cannot use item, null");
  }
  @Override
  public void drawShapes() {
    if (!isVisible()) return;
    float alpha = getAlpha();
    // Shape and sprites don't render together
    shapeRenderer.begin(ShapeType.Filled);
    containerBgColor.a = getAlpha();
    itemBgColor.a = getAlpha();
    shapeRenderer.setColor(containerBgColor);
    // shapeRenderer.rect(getGlobalX(), getGlobalY() + getHeight() - (getHeight()*0.45f), getWidth()*0.5f, getHeight()*0.5f);
    // shapeRenderer.rect(getGlobalX()+getWidth()*0.55f, getGlobalY() + getHeight() - (getHeight()*0.45f), getWidth()*0.45f, getHeight()*0.5f);
    shapeRenderer.rect(getGlobalX(), getGlobalY() + getHeight() - (getHeight()*0.45f), getWidth(), getHeight()*0.5f);
    shapeRenderer.rect(getGlobalX()+getWidth()*0.55f, getGlobalY() + 20, getWidth(), getHeight()*0.45f);
    shapeRenderer.setColor(itemBgColor);
    for (int i = 0; i < inventory.capacity; i++) {
      float x = getGlobalX()+ ((i%cols) * (itemSize + itemPadding + itemMargin.x)) + itemPadding;
      float y = getGlobalY() + getHeight() - itemSize - itemPadding - ((i/cols) * (itemSize + itemMargin.y)); 
      y -= 30;
      x += 13;
      shapeRenderer.circle(x+(itemSize/2), y+(itemSize/2), itemSize/2+itemPadding);
      
    }
    shapeRenderer.end();
    batch.begin();
    for (int i = 0; i < inventory.items.size(); i++) {
      Item item = inventory.items.get(i); 
      float x = getGlobalX()+ ((i%cols) * (itemSize + itemPadding + itemMargin.x)) * itemPadding;
      float y = getGlobalY() + getHeight() - itemSize - itemPadding - ((i/cols) * (itemSize + itemMargin.y));
      y -= 30;
      x += 13;
      if (i != currIndex) item.tint = Color.GRAY;
      else item.tint = Color.WHITE;
      batch.setColor(item.tint.r, item.tint.g, item.tint.b, alpha);
      item.renderStore(batch, x, y, itemSize, itemSize);
      batch.setColor(Color.WHITE);
      batch.setColor(1f, 1f, 1f, alpha);
      quantityLabelFont.draw(batch, String.format("%d/%d", item.quantity, item.maxQuantity), x, y);
      batch.setColor(Color.WHITE);
    }

    if (currItem != null) {
      String desc = currItem.dat.desc;

      itemLabel.setText(currItem.dat.name);
      itemDescLabel.setText(SString.sepLines(currItem.dat.desc, 30));

    }
    else {
      itemLabel.setText("");
      itemDescLabel.setText("");
    }
    batch.end();
  }
}
