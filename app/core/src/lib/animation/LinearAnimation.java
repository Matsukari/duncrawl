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
  @SafeVarargs
  public LinearAnimation(float frameDur, PlayMode mode, T... vals) {
    init(frameDur, mode, vals);
  }
  public LinearAnimation(T val) { init(1, PlayMode.NORMAL, val); }
  @SafeVarargs
  public final void init(float frameDur, PlayMode mode, T... vals) {
    Array<T> arr = new Array<>();
    for (T val : vals) arr.add(val);
    data = new Animation<T>(frameDur, arr, mode);
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

