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
  // Properties
  transient public boolean dead;
  // Bonus
  transient public float bonusAttack = 0;
  transient public float bonusDefense = 0;

  // Only for the next samage taking or inflict
  transient public float nextBonusAttack = 0;
  transient public float nextBonusDefense = 0;

  // Normalized scale, added to physical attributes
  public float elementPower;
  public Element element;
  public Element weakness;
  public ActionState action;
  public enum Element { HOLY, DARK, NONE };

  public static float hurt(Chara attacker, Chara defender) {
    float extraDmg = 0;
    float extraDef = 0;
    if (defender instanceof Player && ((Player)defender).armor != null) extraDef = ((Player)defender).armor.defense;
    else if (attacker instanceof Player && ((Player)attacker).weapon != null) extraDmg = ((Player)attacker).weapon.damage;

    float sustain = (defender.status.getDefense() + extraDef) - (attacker.status.getAttack() + extraDmg);
    if (sustain < 0) defender.status.setHealth(defender.status.health + (int)sustain);
    attacker.status.nextBonusAttack = 0;
    defender.status.nextBonusDefense = 0;
    return sustain;
  }

  transient private TimePeeker healthTimer = new TimePeeker();
  transient private TimePeeker staminaTimer= new TimePeeker();
  public boolean canDo(int cost) { return stamina - cost >= 0; }
  public void setHealth(int value) { health = MathUtils.clamp(value, 0, maxHealth); }
  public void setStamina(int value) { stamina = MathUtils.clamp(value, 0, maxStamina); }
  public float getAttack() { return phyAttack.x + bonusAttack + nextBonusAttack; }
  public float getDefense() { return phyDefense.x + bonusDefense + nextBonusDefense; }
  public void update(float dt) {
    if (healthTimer.sinceLastPeek() >= 10000) {
      setHealth(health + hpRecovery);
      healthTimer.peek();
    }
    if (staminaTimer.sinceLastPeek() >= 7000) {
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
    phyDefense = new Vector2(0, 0);
    elementPower = 1;
    dead = false;
    element = Element.NONE;
    weakness = Element.NONE;
    action = ActionState.IDLE;
  }
}
