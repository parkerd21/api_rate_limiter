package com.parker.api_rate_limiter.service;

import java.util.concurrent.ConcurrentHashMap;

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
}
