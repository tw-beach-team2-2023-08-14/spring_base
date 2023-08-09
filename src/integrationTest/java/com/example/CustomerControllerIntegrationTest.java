package com.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;

public class CustomerControllerIntegrationTest extends BaseIntegrationTest {

  @Test
  @DataSet("retrieve_customer_by_id_successfully.yml")
  public void retrieve_customer_by_id_successfully() {
    given()
        .when()
        .get("/customers/{id}", "1")
        .then()
        .statusCode(200)
        .body("id", equalTo("1"))
        .body("name", equalTo("client"));
  }
}
