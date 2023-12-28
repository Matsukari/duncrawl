package lib.time;

public class Timer {
  public float ms = 0;
  private TimePeeker peeker = new TimePeeker();
  public Timer(float ms) { this.ms = ms; }
  public void start() {
    peeker.peek();
  }
  public boolean isFinished() {
    return peeker.sinceLastPeek() > ms;
  }
  public void reset() {
    peeker.peek();
  }
  
}
