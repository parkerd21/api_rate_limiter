# API Rate Limiter
## This is a simple in progress API Rate Limiter project using Java Spring Boot

### Algorithm Choice
Currently, the project uses IP-based rate limiting with the Fixed Window algorithm. This algorithm was chosen because it is the most simple and easiest to implement. In the future I will be updating to use more sophisticated algorithms like Token Bucket

### How to run and test
**To run locally:**
1. Clone or fork the repo
2. Navigate to the project directory
3. Run the Spring Boot application:

    `./mvnw spring-boot:run`
4. In a browser, navigate to:
    - http://localhost:8080/api/users or http://localhost:8080/api/products

    **To view rate limit response headers (X-RateLimit-Limit, X-RateLimit-Remaining, X-RateLimit-Reset), open browser DevTools → Network tab → select a request → Headers tab**



**To run tests:**

    ./mvnw test
  - Or in VS Code: Open [src/test/java/com/parker/api_rate_limiter/ApiRateLimiterApplicationTests.java] and click the "Run Test" CodeLens above the test method
  
  **The test verifies that:**
  - 100 requests from the same IP succeed
  - The 101st request gets a 429 (Too Many Requests) response
  - A different IP address can still make requests

## Performance Benchmarks

### Load Test Results

**Test Setup:**
- Tool: Apache Bench (ab)
- Hardware: 
  - CPU: AMD Ryzen 7 5800X3D 8-Core Processor (3.98 GHz)
  - RAM: 32.0 GB
- Rate Limit: 100 requests per minute per IP

### Test 1: Rate Limiter Effectiveness
```bash
ab -n 1000 -c 10 http://localhost:8080/api/products
```

**Results:**
- Total requests attempted: 1,000
- Successful requests (200 OK): 100
- Rate limited (429): 900 ✅
- Requests per second: 2,298 req/s
- Average response time: 4.35ms
- 95th percentile latency: 3ms

**Conclusion:** Rate limiter correctly blocks requests exceeding 100/min limit while maintaining fast response times.

### Test 2: Performance Under Normal Load
```bash
ab -n 100 -c 1 http://localhost:8080/api/products
```

**Results:**
- Total requests attempted: 100
- Successful requests (200 OK): 100
- Rate limited (429): 0 ✅
- Requests per second: 87.26 req/s
- Average response time: 11.459ms
- 95th percentile latency: 2ms




