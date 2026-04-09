package com.parker.api_rate_limiter.service;

import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.parker.api_rate_limiter.model.RateLimitEntry;

@Service
@Profile("memory")
public class RateLimiterService implements IRateLimiterService
{
  private final ConcurrentHashMap<String, RateLimitEntry> rateLimits = new ConcurrentHashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(RateLimiterService.class);

  @Override
  public boolean allowRequest(String identifier, int maxRequests, int windowSeconds)
  {
    RateLimitEntry entry = rateLimits.get(identifier);
    long now = Instant.now().getEpochSecond();

    // If it doesn't exist, or is after window create a new RateLimitEntry.
    if (entry == null || now > entry.getWindowStart() + windowSeconds)
    {
      entry = new RateLimitEntry(1, now, windowSeconds);
      rateLimits.put(identifier, entry);
      logger.info("Creating new rate limit entry. key: {}, count: {}, start time: {}", identifier, entry.getCount(), entry.getWindowStart());
      return true;
    }

    if (entry.getCount() >= maxRequests)
    {
      logger.warn("Rate limit exceeded. key: {}, count: {}, start time: {}", identifier, entry.getCount(), entry.getWindowStart());
      return false;
    }

    entry.setCount(entry.getCount() + 1);
    logger.info("Incrementing rate limit entry. key: {}, count: {}, start time: {}", identifier, entry.getCount(), entry.getWindowStart());
    return true;
  }

  @Override
  public int getRemainingRequests(String identifier, int maxRequests)
  {
    RateLimitEntry entry = rateLimits.get(identifier);
    if (entry == null)
    {
      return maxRequests;
    }
    return Math.max(0, maxRequests - entry.getCount());
  }

  @Override
  public long getResetTime(String identifier)
  {
    RateLimitEntry entry = rateLimits.get(identifier);
    if (entry == null)
    {
      return 0;
    }
    return entry.getWindowStart() + entry.getWindowSeconds();
  }
}
