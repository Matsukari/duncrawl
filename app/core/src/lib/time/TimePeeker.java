package lib.time;

import com.badlogic.gdx.utils.TimeUtils;

public class TimePeeker {
  long start = 0;
  long last = 0;
  public TimePeeker() {
    start = TimeUtils.millis();
    last = start;
  }
  public void peek() {
    last = TimeUtils.millis();
  }
  public long sinceLastPeek() {
    return TimeUtils.millis() - last;
  }

}
