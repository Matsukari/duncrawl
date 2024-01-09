package com.leisure.duncraw.art;

public interface Effect {

  public void update(float dt);
  public void stop();
  public void start();
  public boolean isFinished();
  
}
