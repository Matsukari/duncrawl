package lib.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Array;

public class LinearAnimation<T> {
  public Animation<T> data;
  float stateTime = 0f;

  public LinearAnimation(float frameDur, Array<? extends T> vals, PlayMode mode) {
    data = new Animation<T>(frameDur, vals, mode);
  }
  public T current() {
    stateTime += Gdx.graphics.getDeltaTime();
    return data.getKeyFrame(stateTime);
  }
  public boolean isFinished() { return data.isAnimationFinished(stateTime); }
  public void reset() {
    stateTime = 0f;
  }
}

