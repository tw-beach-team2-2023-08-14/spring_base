package com.example;

import com.example.infrastructure.persistence.repository.JpaCustomerRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;

public class CustomerControllerIntegrationTest extends BaseIntegrationTest {
  @Autowired
  private JpaCustomerRepository jpaCustomerRepository;

  @Test
  public void findById_should_success() {
    given().when().get("/customers/{id}", "1").then().statusCode(200);
  }
}
