package com.example.domain.entity

import com.example.common.exception.BusinessException
import com.example.fixture.OrderFixture
import spock.lang.Specification

import java.time.LocalDateTime

import static org.junit.jupiter.api.Assertions.*

class OrderTest extends Specification {
    def "should calculate primitive total Price correctly when having legal price and discount"() {
        given:
        List<ProductDetail> productDetailList = [new ProductDetail(id: 1, name: "water", price: BigDecimal.valueOf(10L), salePrice: BigDecimal.valueOf(8D), quantity: 2)]
        Order order = new Order(
                id: 1,
                customerId: "dcabcfac-6b08-47cd-883a-76c5dc366d88",
                orderId: "order id",
                totalPrice: BigDecimal.valueOf(10L),
                status: OrderStatus.CREATED,
                createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                productDetails: productDetailList)

        when:
        order.calculatePrimitiveTotalPrice()

        then:
        assertEquals(new BigDecimal("20.00"), order.primitiveTotalPrice)
    }


    def "should set Order Status to Cancelled if Order is Created and pass verification"() {
        given:
        def order = OrderFixture
                .orderBuilder()
                .status(OrderStatus.CREATED)
                .customerId(OrderFixture.CUSTOMER_ID)
                .build()
        when:
        order.cancel(OrderFixture.CUSTOMER_ID)

        then:
        assertEquals(OrderStatus.CANCELLED, order.getStatus())
    }

    def "should throw exception when the order is not belongs to the customer"() {
        given:
        def order = OrderFixture
                .orderBuilder()
                .customerId(OrderFixture.CUSTOMER_ID)
                .build()
        when:
        order.cancel("Not valid customer id")

        then:
        thrown(BusinessException)
    }

    def "should throw exception when pre check cancel order status already being cancelled"() {
        given:

        def order = OrderFixture
                .orderBuilder()
                .status(OrderStatus.CANCELLED)
                .build()

        when:
        order.cancel(OrderFixture.CUSTOMER_ID)

        then:
        thrown(BusinessException)
    }
}
