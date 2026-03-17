package com.parker.api_rate_limiter.model;

public class RateLimitEntry {

  private int count;
  private long windowStart;
  private int windowSeconds;

  public RateLimitEntry(int count, long windowStart, int windowSeconds)
  {
    this.count = count;
    this.windowStart = windowStart;
    this.windowSeconds = windowSeconds;
  }

  public int getCount()
  {
    return count;
  }

  public void setCount(int count)
  {
    this.count = count;
  }

  public long getWindowStart()
  {
    return windowStart;
  }

  public void setWindowStart(long windowStart)
  {
    this.windowStart = windowStart;
  }

  public int getWindowSeconds()
  {
    return windowSeconds;
  }

  public void setWindowSeconds(int windowSeconds)
  {
    this.windowSeconds = windowSeconds;
  }
}
