package com.parker.api_rate_limiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class ProductController {
  List<String> users = new ArrayList<>();

  @GetMapping("/products")
  public List<String> getProducts()
  {
    return List.of("Product 1", "Product 2", "Product 3");
  }

  @GetMapping("/users")
  public List<String> getUsers()
  {
    return List.of("user1", "user2", "user3");
  }

  @PostMapping("/users")
  public String createUser(String user)
  {
    users.add(user);
    return user;
  }
}
