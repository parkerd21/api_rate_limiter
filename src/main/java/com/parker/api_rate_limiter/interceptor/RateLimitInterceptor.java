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
    String id = request.getRemoteAddr();

    if (!rateLimiterService.allowRequest(id))
    {
      response.setStatus(429);
      response.setHeader("X-RateLimit-Limit", "10");
      response.setHeader("X-RateLimit-Remaining", "0");
      response.setHeader("X-RateLimit-Reset", String.valueOf(rateLimiterService.getResetTime(id)));
      response.getWriter().print("{\"error\": \"Too many requests. Please try again later.\"}");
      return false;
    }
    response.setHeader("X-RateLimit-Limit", "10");
    response.setHeader("X-RateLimit-Remaining", String.valueOf(rateLimiterService.getRemainingRequests(id)));
    return true;
  }
  
}
