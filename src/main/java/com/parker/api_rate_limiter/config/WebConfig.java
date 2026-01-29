package com.parker.api_rate_limiter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.parker.api_rate_limiter.interceptor.RateLimitInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
  private final RateLimitInterceptor rateLimitInterceptor;

  public WebConfig(RateLimitInterceptor rateLimitInterceptor)
  {
    this.rateLimitInterceptor = rateLimitInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) 
  {
    registry.addInterceptor(rateLimitInterceptor);
  }
}
