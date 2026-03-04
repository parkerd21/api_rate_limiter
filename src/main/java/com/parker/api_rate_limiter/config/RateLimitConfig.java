package com.parker.api_rate_limiter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="app.ratelimit")
public class RateLimitConfig 
{
  private int maxRequests;
  private long windowSeconds;

  public int getMaxRequests()
  {
    return maxRequests;
  }

  public void setMaxRequests(int maxRequests)
  {
    this.maxRequests = maxRequests;
  }

  public long getwindowSeconds()
  {
    return windowSeconds;
  }

  public void setWindowSeconds(long windowSeconds)
  {
    this.windowSeconds = windowSeconds;
  }
}
