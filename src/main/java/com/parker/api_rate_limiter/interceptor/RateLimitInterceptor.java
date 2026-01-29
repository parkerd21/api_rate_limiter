package com.parker.api_rate_limiter.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.parker.api_rate_limiter.service.RateLimiterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitInterceptor implements HandlerInterceptor{

  private final RateLimiterService rateLimiterService;

  public RateLimitInterceptor(RateLimiterService rateLimiterService)
  {
    this.rateLimiterService = rateLimiterService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
  {
    return rateLimiterService.allowRequest("Test");
  }
  
}
