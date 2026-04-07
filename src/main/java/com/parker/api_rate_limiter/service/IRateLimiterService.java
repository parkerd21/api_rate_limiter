package com.parker.api_rate_limiter.service;

public interface IRateLimiterService {
  boolean allowRequest(String identifier, int maxRequests, int windowSeconds);
  int getRemainingRequests(String identifier, int maxRequests);
  long getResetTime(String identifier);
}
