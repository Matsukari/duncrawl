package com.leisure.duncraw.art.chara;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import lib.time.TimePeeker;

public class Status {
  public int health;
  public int stamina;
  public int maxHealth;
  public int maxStamina;
  // Recovery per miles (3 sec)
  public int hpRecovery;
  public int stRecovery;
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

  transient private TimePeeker healthTimer = new TimePeeker();
  transient private TimePeeker staminaTimer= new TimePeeker();
  public boolean canDo(int cost) { return stamina - cost >= 0; }
  public void setHealth(int value) { health = MathUtils.clamp(value, 0, maxHealth); }
  public void setStamina(int value) { stamina = MathUtils.clamp(value, 0, maxStamina); }
  public float getAttack() { return phyAttack.x + bonusAttack; }
  public float getDefense() { return phyDefense.x + bonusDefense; }
  public void update(float dt) {
    if (healthTimer.sinceLastPeek() >= 5000) {
      setHealth(health + hpRecovery);
      healthTimer.peek();
    }
    if (staminaTimer.sinceLastPeek() >= 3000) {
      setStamina(stamina + stRecovery);
      staminaTimer.peek();
    }
  }
  public void reset() {
    health = 100;
    maxHealth = 100;
    stamina = 100;
    hpRecovery = 2;
    stRecovery = 3;
    maxStamina = 100;
    phyAttack = new Vector2(1, 1);
    phyDefense = new Vector2(1, 1);
    elementPower = 1;
    element = Element.NONE;
    weakness = Element.NONE;
    action = ActionState.IDLE;
  }
}
