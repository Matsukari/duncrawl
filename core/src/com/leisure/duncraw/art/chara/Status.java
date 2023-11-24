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
  // Normalized scale, added to physical attributes
  public float elementPower;
  public Element element;
  public Element weakness;
  public enum Element { HOLY, DARK, NONE };
  public enum State { MOVING, IDLE, ATTACKING, INTERACTING };
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

  }
}
