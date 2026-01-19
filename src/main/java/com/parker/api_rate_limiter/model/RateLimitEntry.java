package com.parker.api_rate_limiter.model;

public class RateLimitEntry {

  private int count;
  private long windowStart;

  public RateLimitEntry(int count, long windowStart)
  {
    this.count = count;
    this.windowStart = windowStart;
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
}
