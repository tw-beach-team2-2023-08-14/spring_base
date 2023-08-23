package com.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import com.github.database.rider.core.api.dataset.DataSet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderControllerIntegrationTest extends BaseIntegrationTest {
  String ORDER_ID_ONE = "ad3c8e37-88ea-440a-a7a0-531b829fb5c1";
  String ORDER_ID_TWO = "ad3c8e37-88ea-440a-a7a0-531b829fb5c2";

  String ORDER_ID_NOT_EXIST = "ad3c8e37-88ea-440a-a7a0-531b829fb5c3";
  String CUSTOMER_ID_ONE = "dcabcfac-6b08-47cd-883a-76c5dc366d88";
  String CUSTOMER_ID_NOT_EXIST = "dcabcfac-6b08-47cd-883a-76c5dc366d85";

  @Test
  @DataSet("retrieve_orders_on_order_table.yml")
  public void should_retrieve_order_list_by_customer_id_successfully() {
    given()
        .when()
        .get("/orders?customerId=" + CUSTOMER_ID_ONE)
        .then()
        .statusCode(OK.value())
        .body("[0].id", equalTo(1))
        .body("[0].customerId", equalTo(CUSTOMER_ID_ONE))
        .body("[0].orderId", equalTo(ORDER_ID_ONE))
        .body("[0].primitiveTotalPrice", equalTo(20.00F))
        .body("[0].totalPrice", equalTo(16.00F))
        .body("[0].status", equalTo("CREATED"))
        .body("[0].createTime", equalTo("2023-08-10T12:35:13"))
        .body("[0].productDetails[0].id", equalTo(1))
        .body("[0].productDetails[0].name", equalTo("water"))
        .body("[0].productDetails[0].price", equalTo(10.00F))
        .body("[0].productDetails[0].salePrice", equalTo(8.00F))
        .body("[0].productDetails[0].quantity", equalTo(2))
        .body("[0].productDetails[0].totalPreferentialPrice", equalTo(4.00F))
        .body("[1].id", equalTo(2))
        .body("[1].customerId", equalTo(CUSTOMER_ID_ONE))
        .body("[1].orderId", equalTo(ORDER_ID_TWO))
        .body("[1].totalPrice", equalTo(20.00F))
        .body("[1].status", equalTo("CREATED"))
        .body("[1].createTime", equalTo("2023-08-10T12:35:13"))
        .body("[1].productDetails[0].id", equalTo(1))
        .body("[1].productDetails[0].name", equalTo("cola"))
        .body("[1].productDetails[0].price", equalTo(10.00F))
        .body("[1].productDetails[0].salePrice", equalTo(10.00F))
        .body("[1].productDetails[0].quantity", equalTo(2))
        .body("[1].productDetails[0].totalPreferentialPrice", equalTo(0.00F))
        .body("size()", equalTo(2));
  }

  @Test
  @DataSet("retrieve_orders_on_order_table.yml")
  public void should_retrieve_order_by_customer_id_and_order_id_successfully() {

    given()
        .when()
        .get("/orders/" + ORDER_ID_ONE + "?customerId=" + CUSTOMER_ID_ONE)
        .then()
        .statusCode(OK.value())
        .body("id", equalTo(1))
        .body("customerId", equalTo(CUSTOMER_ID_ONE))
        .body("orderId", equalTo(ORDER_ID_ONE))
        .body("primitiveTotalPrice", equalTo(20.00F))
        .body("totalPrice", equalTo(16.00F))
        .body("status", equalTo("CREATED"))
        .body("createTime", equalTo("2023-08-10T12:35:13"))
        .body("productDetails[0].id", equalTo(1))
        .body("productDetails[0].name", equalTo("water"))
        .body("productDetails[0].price", equalTo(10.00F))
        .body("productDetails[0].salePrice", equalTo(8.00F))
        .body("productDetails[0].quantity", equalTo(2))
        .body("productDetails[0].totalPreferentialPrice", equalTo(4.00F));
  }

  @Test
  @DataSet("retrieve_orders_on_order_table.yml")
  public void should_return_error_response_when_order_not_belong_to_this_customer() {
    given()
        .when()
        .get("/orders/" + ORDER_ID_ONE + "?customerId=" + CUSTOMER_ID_NOT_EXIST)
        .then()
        .statusCode(INTERNAL_SERVER_ERROR.value());
  }

  @Test
  @DataSet("retrieve_orders_on_order_table.yml")
  public void should_return_error_response_when_order_not_exist() {
    given()
        .when()
        .get("/orders/" + ORDER_ID_NOT_EXIST + "?customerId=" + CUSTOMER_ID_ONE)
        .then()
        .statusCode(INTERNAL_SERVER_ERROR.value());
  }

  @Test
  @DataSet("save_order_successfully.yml")
  public void should_save_order_and_return_order_id_successfully() {

    JSONObject orderProductOne = new JSONObject();
    orderProductOne.putAll(Map.of("productId", 1001, "quantity", 1L));
    JSONObject orderProductTwo = new JSONObject();
    orderProductTwo.putAll(Map.of("productId", 1002, "quantity", 2L));
    JSONObject orderProductThree = new JSONObject();
    orderProductThree.putAll(Map.of("productId", 1003, "quantity", 3L));

    JSONArray orderProducts = new JSONArray();
    orderProducts.addAll(List.of(orderProductOne, orderProductTwo, orderProductThree));

    JSONObject orderRequest = new JSONObject();
    orderRequest.putAll(
        Map.of(
            "customerId", "dcabcfac-6b08-47cd-883a-76c5dc366d88", "orderProducts", orderProducts));
    String orderReqBody = orderRequest.toJSONString();

    Response response =
        given().contentType(ContentType.JSON).body(orderReqBody).when().post("/orders");
    Assertions.assertEquals(OK.value(), response.statusCode());
    Assertions.assertNotNull(response.body());
  }
}
