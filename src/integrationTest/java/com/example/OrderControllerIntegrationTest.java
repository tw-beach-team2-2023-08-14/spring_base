package com.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.OK;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;

public class OrderControllerIntegrationTest extends BaseIntegrationTest {

  @Test
  @DataSet("retrieve_orders_on_order_table.yml")
  public void should_retrieve_order_list_by_customer_id_successfully() {
    given()
        .when()
        .get("/orders/{customer_id}", "abcd")
        .then()
        .statusCode(OK.value())
        .body("[0].id", equalTo(1))
        .body("[0].customerId", equalTo("abcd"))
        .body("[0].totalPrice", equalTo(20.0F))
        .body("[0].status", equalTo("CREATED"))
        .body("[0].productDetails[0].name", equalTo("water"));
  }
}
