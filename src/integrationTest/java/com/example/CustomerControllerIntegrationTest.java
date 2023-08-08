package com.example;

import static io.restassured.RestAssured.given;

import com.example.infrastructure.persistence.repository.JpaCustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerControllerIntegrationTest extends BaseIntegrationTest {
  @Autowired private JpaCustomerRepository jpaCustomerRepository;

  @Test
  public void findById_should_success() {
    given().when().get("/customers/{id}", "1").then().statusCode(200);
  }
}
