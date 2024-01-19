package lib.time;

public class Timer {
  public float ms = 0;
  public boolean hasStarted = false;
  private TimePeeker peeker = new TimePeeker();
  public Timer(float ms) { this.ms = ms; }
  public void start() {
    hasStarted = true;
    peeker.peek();
  }
  public boolean isFinished() {
    return peeker.sinceLastPeek() > ms && hasStarted;
  }
  public void stop() {
    hasStarted = false;
  }
  public void reset() {
    peeker.peek();
  }
  public float normalize() {
    if (!hasStarted) return 0;
    return Math.min(peeker.sinceLastPeek() / ms, 1f);
  }
  public boolean isTicking() {
    return hasStarted;
  }
  
}
