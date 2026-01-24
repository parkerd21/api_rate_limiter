package com.parker.api_rate_limiter.service;

import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.parker.api_rate_limiter.model.RateLimitEntry;

@Service
public class RateLimiterService {

  private final ConcurrentHashMap<String, RateLimitEntry> rateLimits = new ConcurrentHashMap<>();
  private final int maxRequests;
  private final long windowMs;

  public RateLimiterService ()
  {
    maxRequests = 10;
    windowMs = 60000; // 1 minute
  }

  public boolean allowRequest(String identifier)
  {
    
    RateLimitEntry entry = rateLimits.get(identifier);
    long now = Instant.now().getEpochSecond();

    // If it doesn't exist, create a new RateLimitEntry. Use current timestamp and count of 1
    if (entry == null || now > entry.getWindowStart() + windowMs)
    {
      entry = new RateLimitEntry(1, now);
      rateLimits.put(identifier, entry);
      return true;
    }

    if (entry.getCount() >= maxRequests)
    {
      return false;
    }

    entry.setCount(entry.getCount() + 1);
    return true;
  }

  public int getRemainingRequests(String identifier)
  {
    RateLimitEntry entry = rateLimits.get(identifier);
    if (entry == null)
    {
      return maxRequests;
    }
    return Math.max(0, maxRequests - entry.getCount());
  }

  public long getResetTime(String identifier)
  {
    RateLimitEntry entry = rateLimits.get(identifier);
    if (entry == null)
    {
      return 0;
    }
    
    return entry.getWindowStart() + windowMs;
  }
}
