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
        .get("/orders?customer_id=dcabcfac-6b08-47cd-883a-76c5dc366d88")
        .then()
        .statusCode(OK.value())
        .body("[0].id", equalTo(1))
        .body("[0].customerId", equalTo("dcabcfac-6b08-47cd-883a-76c5dc366d88"))
        .body("[0].orderId", equalTo("orderId1"))
        .body("[0].totalPrice", equalTo(20.0F))
        .body("[0].status", equalTo("CREATED"))
        .body("[0].createTime", equalTo("2023-08-10T12:35:13"))
        .body("[0].productDetails[0].name", equalTo("water"))
        .body("[1].id", equalTo(2))
        .body("[1].customerId", equalTo("dcabcfac-6b08-47cd-883a-76c5dc366d88"))
        .body("[1].orderId", equalTo("orderId2"))
        .body("[1].totalPrice", equalTo(20.0F))
        .body("[1].status", equalTo("CREATED"))
        .body("[1].createTime", equalTo("2023-08-10T12:35:13"))
        .body("[1].productDetails[0].name", equalTo("cola"))
        .body("size()", equalTo(2));
  }

  @Test
  @DataSet("retrieve_orders_on_order_table.yml")
  public void should_retrieve_order_list_by_customer_id_and_order_id_successfully() {
    given()
        .when()
        .get("/orders?customer_id=dcabcfac-6b08-47cd-883a-76c5dc366d88&order_id=orderId1")
        .then()
        .statusCode(OK.value())
        .body("[0].id", equalTo(1))
        .body("[0].customerId", equalTo("dcabcfac-6b08-47cd-883a-76c5dc366d88"))
        .body("[0].orderId", equalTo("orderId1"))
        .body("[0].totalPrice", equalTo(20.0F))
        .body("[0].status", equalTo("CREATED"))
        .body("[0].createTime", equalTo("2023-08-10T12:35:13"))
        .body("[0].productDetails[0].name", equalTo("water"))
        .body("size()", equalTo(1));
  }
}
