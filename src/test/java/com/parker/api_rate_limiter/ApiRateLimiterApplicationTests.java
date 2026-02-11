package com.parker.api_rate_limiter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ApiRateLimiterApplicationTests {

  @Autowired
  private MockMvc mockMvc;

	@Test
	void testRateLimit() throws Exception{
    // Do 100 successful requests
    for (int i = 0; i < 100; i++)
    {
      mockMvc.perform(get("/api/products"))
        .andExpect(status().isOk());
    }

    // 101st request is over limit and should not go through
    mockMvc.perform(get("/api/products"))
      .andExpect(status().isTooManyRequests());

    // verify other IP address will still go through
    mockMvc.perform(get("/api/products")
      .with(request -> {
        request.setRemoteAddr("127.0.0.2");
        return request;
      })
    ).andExpect(status().isOk());
	}
}
