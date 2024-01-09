
package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;

public class AttackState extends State {
  public Chara target;
  public AttackState(Chara target) { this.target = target; }
  @Override
  public void init(Chara s) {
    super.init(s);
    if (target != null)
      target.setState(new HurtState(chara));
  }
  @Override
  public void update(float dt) {

  }
  
}
