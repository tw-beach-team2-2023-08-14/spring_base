package com.example.domain.entity

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
        assertEquals(BigDecimal.valueOf(20), order.primitiveTotalPrice)
    }
}
