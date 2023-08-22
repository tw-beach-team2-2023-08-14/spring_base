package com.example.fixture

import com.example.domain.entity.Order
import com.example.domain.entity.OrderStatus
import com.example.domain.entity.ProductDetail
import com.example.presentation.vo.OrderListDto

import java.time.LocalDateTime;

class OrderFixture {
    public static final String CUSTOMER_ID = "dcabcfac-6b08-47cd-883a-76c5dc366d88"
    public static final String ORDER_ID_ONE = "dcabcfac-6b08-47cd-883a-76c5dc366d00"
    public static final String ORDER_ID_TWO = "dcabcfac-6b08-47cd-883a-76c5dc366d01"

    public static final List<ProductDetail> PRODUCT_DETAIL_LIST
            = List.of(new ProductDetail(id: 1, name: "water", price: BigDecimal.valueOf(10L), quantity: 2))

    public static final Order ORDER_ONE = new Order(
            id: 1,
            customerId: CUSTOMER_ID,
            orderId: ORDER_ID_ONE,
            primitiveTotalPrice: new BigDecimal("20.00"),
            totalPrice: new BigDecimal("10.00"),
            status: com.example.domain.entity.OrderStatus.CREATED,
            createTime: java.time.LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            updateTime: java.time.LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            productDetails: PRODUCT_DETAIL_LIST
    )

    public static final Order ORDER_TWO = new Order(
            id: 2,
            customerId: CUSTOMER_ID,
            orderId: ORDER_ID_TWO,
            primitiveTotalPrice: new BigDecimal("20.00"),
            totalPrice: new BigDecimal("10.00"),
            status: com.example.domain.entity.OrderStatus.CREATED,
            createTime: java.time.LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            updateTime: java.time.LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            productDetails: PRODUCT_DETAIL_LIST
    )

    public static final OrderListDto ORDER_DTO_ONE = new OrderListDto(
            id: 1,
            customerId: OrderFixture.CUSTOMER_ID,
            orderId: OrderFixture.ORDER_ID_ONE,
            primitiveTotalPrice: new BigDecimal("20.00"),
            totalPrice: new BigDecimal("10.00"),
            createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            status: OrderStatus.CREATED,
            productDetails: PRODUCT_DETAIL_LIST
    )

    public static final OrderListDto ORDER_DTO_TWO = new OrderListDto(
            id: 2,
            customerId: OrderFixture.CUSTOMER_ID,
            orderId: OrderFixture.ORDER_ID_TWO,
            primitiveTotalPrice: new BigDecimal("20.00"),
            totalPrice: new BigDecimal("10.00"),
            createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
            status: OrderStatus.CREATED,
            productDetails: PRODUCT_DETAIL_LIST
    )
}
