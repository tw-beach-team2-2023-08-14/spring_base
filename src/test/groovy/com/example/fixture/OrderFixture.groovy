package com.example.fixture

import com.example.domain.entity.Order
import com.example.domain.entity.OrderStatus
import com.example.domain.entity.ProductDetail
import com.example.infrastructure.persistence.entity.OrderPo
import com.example.presentation.vo.OrderDto
import com.example.presentation.vo.OrderProductDetailDto
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

import java.time.LocalDateTime

class OrderFixture {
    public static final String CUSTOMER_ID = "dcabcfac-6b08-47cd-883a-76c5dc366d88"
    public static final String ORDER_ID_ONE = "dcabcfac-6b08-47cd-883a-76c5dc366d00"
    public static final String ORDER_ID_TWO = "dcabcfac-6b08-47cd-883a-76c5dc366d01"

    public static final List<ProductDetail> PRODUCT_DETAIL_LIST
            = List.of(new ProductDetail(id: 1, name: "water", price: new BigDecimal("10.00"), salePrice: new BigDecimal("9.00"), totalPreferentialPrice: new BigDecimal("2.00"), quantity: 2))

    public static final List<OrderProductDetailDto> PRODUCT_DTO_DETAIL_LIST
            = List.of(new OrderProductDetailDto(id: 1, name: "water", price: new BigDecimal("10.00"), salePrice: new BigDecimal("9.00"), totalPreferentialPrice: new BigDecimal("2.00"), quantity: 2))


    public static final String PRODUCT_DETAIL_LIST_JSON_STRING = '''
 [{
                         "id": 1,
                         "name": "water",
                         "price": 10.00,
                         "salePrice": 9.00,
                         "totalPreferentialPrice": 2.00,
                         "quantity": 2
                     }]

'''

    public static final Order ORDER_ONE = new Order(
            id: 1,
            customerId: CUSTOMER_ID,
            orderId: ORDER_ID_ONE,
            primitiveTotalPrice: new BigDecimal("20.00"),
            totalPrice: new BigDecimal("10.00"),
            status: OrderStatus.CREATED,
            createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            productDetails: PRODUCT_DETAIL_LIST
    )

    public static final Order ORDER_TWO = new Order(
            id: 2,
            customerId: CUSTOMER_ID,
            orderId: ORDER_ID_TWO,
            primitiveTotalPrice: new BigDecimal("20.00"),
            totalPrice: new BigDecimal("10.00"),
            status: OrderStatus.CREATED,
            createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            productDetails: PRODUCT_DETAIL_LIST
    )

    public static final OrderDto ORDER_DTO_ONE = new OrderDto(
            id: 1,
            customerId: CUSTOMER_ID,
            orderId: ORDER_ID_ONE,
            primitiveTotalPrice: new BigDecimal("20.00"),
            totalPrice: new BigDecimal("10.00"),
            createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            status: OrderStatus.CREATED,
            productDetails: PRODUCT_DTO_DETAIL_LIST
    )

    public static final OrderDto ORDER_DTO_TWO = new OrderDto(
            id: 2,
            customerId: CUSTOMER_ID,
            orderId: ORDER_ID_TWO,
            primitiveTotalPrice: new BigDecimal("20.00"),
            totalPrice: new BigDecimal("10.00"),
            createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            status: OrderStatus.CREATED,
            productDetails: PRODUCT_DTO_DETAIL_LIST
    )

    public static final OrderPo ORDER_PO = new OrderPo(
            id: 1,
            customerId: CUSTOMER_ID,
            orderId: ORDER_ID_ONE,
            totalPrice: BigDecimal.valueOf(10L),
            status: OrderStatus.CREATED,
            createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            productDetails: PRODUCT_DETAIL_LIST_JSON_STRING
    )

    public static final Order ORDER_WITHOUT_PRIMITIVE_TOTAL_PRICE = new Order(
            id: 1,
            customerId: CUSTOMER_ID,
            orderId: ORDER_ID_ONE,
            totalPrice: BigDecimal.valueOf(10L),
            status: OrderStatus.CREATED,
            createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            productDetails: PRODUCT_DETAIL_LIST
    )

    static Order.OrderBuilder orderBuilder() {
        return Order.builder()
                .id(1)
                .customerId(CUSTOMER_ID)
                .orderId(ORDER_ID_ONE)
                .totalPrice(BigDecimal.valueOf(10L))
                .status(OrderStatus.CREATED)
                .createTime(LocalDateTime.of(2023, 8, 8, 10, 30, 0))
                .updateTime(LocalDateTime.of(2023, 8, 8, 10, 30, 0))
                .productDetails(PRODUCT_DETAIL_LIST)
    }
}
