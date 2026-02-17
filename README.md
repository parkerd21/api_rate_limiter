<h1>API Rate Limiter</h1>

<h2>This is a simple in progress API Rate Limiter project using Java Spring Boot</h2>

<h3>Algorithm Choice</h3>
<p>Currently, the project uses IP-based rate limiting with the Fixed Window algorithm. This algorithm was chosen because it is the most simple and easiest to implement. In the future I will be updating to use more sophisticated algorithms like Token Bucket</p>

<h3>How to run and test</h3>
<h4>To run locally:</h4>
<ol>
  <li>Clone or fork repo</li>
  <li>Run Spring Boot app
  <li>In a browser navigate to http://localhost:8080/api/users or http://localhost:8080/api/products</li>
</ol>
<p>Note, you can view the response headers, <b>X-Ratelimit-Limit</b>, <b>X-Ratelimit-Remaining</b>, <b>Keep-Alive</b> by opening the browser dev tools, looking at the Network tab, selecting the request, and clicking on the Headers tab.</p>
<h4>To run tests:</h4>
<ol>
  <li>File location: src\test\java\com\parker\api_rate_limiter\ApiRateLimiterApplicationTests.java</li>
  <li>Click to run tests
</ol>





