package com.parker.api_rate_limiter.service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Profile;
import io.micrometer.common.util.StringUtils;


@Service
@Profile("redis")
public class RedisRateLimiterService implements IRateLimiterService
{
  private final RedisTemplate<String, String> redisTemplate;
  private static final Logger logger = LoggerFactory.getLogger(RateLimiterService.class);

  public RedisRateLimiterService(RedisTemplate<String, String> redisTemplate)
  {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public boolean allowRequest(String identifier, int maxRequests, int windowSeconds)
  {
    String key = "ratelimit:" + identifier;
    long now = Instant.now().getEpochSecond();

    // Get current count
    String countStr = redisTemplate.opsForValue().get(key);

    if (countStr == null)
    {
      // First request in this window
      redisTemplate.opsForValue().set(key, "1", windowSeconds, TimeUnit.SECONDS);
      redisTemplate.opsForValue().set(key + ":start", String.valueOf(now), windowSeconds, TimeUnit.SECONDS);
      logger.info("Creating new rate limit entry. key: {}, count: {}, start time: {}", key, 1, String.valueOf(now));
      return true;
    }

    long count = Long.parseLong(countStr);
    long windowStart = Long.parseLong(redisTemplate.opsForValue().get(key + ":start"));

    if (count >= maxRequests)
    {
      logger.warn("Rate limit exceeded. key: {}, count: {}, start time: {}", identifier, count, windowStart);
      return false;
    }

    count = redisTemplate.opsForValue().increment(key);
    logger.info("Incrementing rate limit entry. key: {}, count: {}, start time: {}", key, count, windowStart);

    return true;
  }

  @Override
  public int getRemainingRequests(String identifier, int maxRequests)
  {
    String key = "ratelimit:" + identifier;
    String countStr = redisTemplate.opsForValue().get(key);
    if (StringUtils.isEmpty(countStr))
    {
      return maxRequests;
    }
    
    int count = Integer.parseInt(countStr);
    return Math.max(0, maxRequests - count);
  }

  @Override
  public long getResetTime(String identifier)
  {
    String key = "ratelimit:" + identifier;
    String startTime = redisTemplate.opsForValue().get(key + ":start");
    Long endTime = redisTemplate.getExpire(key);
    if (StringUtils.isEmpty(startTime))
    {
      return 0;
    }
    return Long.parseLong(startTime) + endTime; 
  }
}