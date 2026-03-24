package com.parker.api_rate_limiter.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.parker.api_rate_limiter.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.parker.api_rate_limiter.config.annotations.RateLimit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor 
{

  private final RateLimiterService rateLimiterService;

  public RateLimitInterceptor(RateLimiterService rateLimiterService)
  {
    this.rateLimiterService = rateLimiterService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
  {
    if (!(handler instanceof HandlerMethod))
    {
      return true;
    }

    HandlerMethod handlerMethod = (HandlerMethod) handler;
    RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);

    int maxRequests = 100;
    int windowSeconds = 60;

    if (rateLimit != null)
    {
      maxRequests = rateLimit.requests();
      windowSeconds = rateLimit.windowSeconds();
    }

    String id = request.getHeader("X-User-ID");
    if (id == null || id.isEmpty())
    {
      id = request.getRemoteAddr();
    }

    // add endpoint to id so each endpoint has separate limit
    String key = id + ":" + request.getRequestURI();

    if (!rateLimiterService.allowRequest(key, maxRequests, windowSeconds))
    {
      response.setStatus(429);
      response.setHeader("X-RateLimit-Limit", String.valueOf(maxRequests));
      response.setHeader("X-RateLimit-Remaining", "0");
      response.setHeader("X-RateLimit-Reset", String.valueOf(rateLimiterService.getResetTime(key)));
      response.getWriter().print("{\"error\": \"Too many requests. Please try again later.\"}");
      return false;
    }
    response.setHeader("X-RateLimit-Limit", String.valueOf(maxRequests));
    response.setHeader("X-RateLimit-Remaining", String.valueOf(rateLimiterService.getRemainingRequests(key, maxRequests)));
    return true;
  }
}
