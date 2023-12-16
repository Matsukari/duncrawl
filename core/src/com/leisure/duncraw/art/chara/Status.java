package com.leisure.duncraw.art.chara;

import com.badlogic.gdx.math.Vector2;

public class Status {
  public int health;
  public int maxHealth;
  public int stamina;
  public int maxStamina;
  // Base attributes
  public Vector2 phyAttack;
  public Vector2 phyDefense;
  // Bonus
  transient public float bonusAttack;
  transient public float bonusDefense;
  // Normalized scale, added to physical attributes
  public float elementPower;
  public Element element;
  public Element weakness;
  public ActionState action;
  public enum Element { HOLY, DARK, NONE };

  public float getAttack() { return phyAttack.x + bonusAttack; }
  public float getDefense() { return phyDefense.x + bonusDefense; }
  public void reset() {
    health = 100;
    maxHealth = 100;
    stamina = 100;
    maxStamina = 100;
    phyAttack = new Vector2(1, 1);
    phyDefense = new Vector2(1, 1);
    elementPower = 1;
    element = Element.NONE;
    weakness = Element.NONE;
    action = ActionState.IDLE;
  }
  public void update() {
    action = ActionState.IDLE;
  }
}
