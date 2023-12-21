package com.leisure.duncraw.art.chara.observers;

import com.badlogic.gdx.audio.Sound;
import com.leisure.duncraw.Audio;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.HurtState;
import com.leisure.duncraw.logging.Logger;

public class SoundBehaviour extends Observer {
  public Sound attackSound;
  public Sound hurtSound;
  @Override
  public void init(Chara c) {
    super.init(c);
    try {
      attackSound = Audio.getSound(chara.sounds.get("attack"));
      hurtSound = Audio.getSound(chara.sounds.get("hurt"));
    } catch (Exception e) {}
    // if (attackSound == null) Logger.log("SoundBehaviour", "Got attack sound");
  }
  @Override
  public void invoke(State state) {
    if (state instanceof AttackState && attackSound != null) attackSound.play();
    else if (state instanceof HurtState && hurtSound != null) hurtSound.play();
  }
  @Override
  public Observer copy() {
    return new SoundBehaviour();
  }
}
