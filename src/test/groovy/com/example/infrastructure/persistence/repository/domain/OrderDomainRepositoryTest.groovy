package com.example.infrastructure.persistence.repository.domain

import com.example.domain.entity.Order
import com.example.domain.entity.OrderStatus
import com.example.domain.entity.ProductDetail
import com.example.fixture.OrderFixture
import com.example.infrastructure.persistence.assembler.OrderProductDetailsDataMapper
import com.example.infrastructure.persistence.entity.OrderPo
import com.example.infrastructure.persistence.repository.JpaOrderRepository
import org.assertj.core.api.Assertions
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import java.time.LocalDateTime

class OrderDomainRepositoryTest extends Specification {
    JpaOrderRepository jpaOrderRepository = Mock()
    ObjectMapper objectMapper = new ObjectMapper()
    OrderProductDetailsDataMapper orderProductDetailsDataMapper = new OrderProductDetailsDataMapper()
    OrderDomainRepository orderDomainRepository = new OrderDomainRepository(jpaOrderRepository, orderProductDetailsDataMapper)

    def "Should save order and return order Id"() {
        given:
        LocalDateTime createTime = LocalDateTime.now()
        LocalDateTime updateTime = LocalDateTime.now()
        String orderIdToSave = UUID.randomUUID().toString()

        List<ProductDetail> productDetails = List.of(new ProductDetail(1, "productDetailName1", BigDecimal.ONE, BigDecimal.valueOf(0.9D), new BigDecimal("0.1000"), 1))

        String productDetailsToSave = objectMapper.writeValueAsString(productDetails)

        Order orderToSave = new Order(null, "consumerId", orderIdToSave, null, BigDecimal.ONE, OrderStatus.CREATED, createTime, updateTime, productDetails)

        OrderPo savedOrderPo = new OrderPo(Integer.valueOf(1), orderIdToSave, "consumerId", BigDecimal.ONE, OrderStatus.CREATED, createTime, updateTime, productDetailsToSave)

        jpaOrderRepository.save(_) >> savedOrderPo

        when:
        def orderId = orderDomainRepository.save(orderToSave)

        then:
        Assertions.assertThat(orderId).usingRecursiveComparison().isEqualTo(orderIdToSave)
    }


    def "should retrieve order list by customer id"() {
        given:
        def jsonString =
                ''' [{
                         "id": 1,
                         "name": "water",
                         "price": 10,
                         "quantity": 2
                     }]
                '''
        List<OrderPo> jpaOrdersList = [
                new OrderPo(
                        id: 1,
                        customerId: OrderFixture.CUSTOMER_ID,
                        orderId: "order id",
                        totalPrice: BigDecimal.valueOf(10L),
                        status: OrderStatus.CREATED,
                        createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        productDetails: jsonString.toString()
                ),
                new OrderPo(
                        id: 2,
                        customerId: OrderFixture.CUSTOMER_ID,
                        orderId: "order id",
                        totalPrice: BigDecimal.valueOf(10L),
                        status: OrderStatus.CREATED,
                        createTime: LocalDateTime.of(2023, 8, 8, 11, 30, 0),
                        updateTime: LocalDateTime.of(2023, 8, 8, 11, 30, 0),
                        productDetails: jsonString.toString()
                ),
        ]

        jpaOrderRepository.findByCustomerId(OrderFixture.CUSTOMER_ID) >> jpaOrdersList

        List<ProductDetail> productDetailList = [new ProductDetail(id: 1, name: "water", price: BigDecimal.valueOf(10L), quantity: 2)]

        List<Order> expectedOrder = [
                new Order(
                        id: 1,
                        customerId: OrderFixture.CUSTOMER_ID,
                        orderId: "order id",
                        totalPrice: BigDecimal.valueOf(10L),
                        status: OrderStatus.CREATED,
                        createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        productDetails: productDetailList
                ),
                new Order(
                        id: 2,
                        customerId: OrderFixture.CUSTOMER_ID,
                        orderId: "order id",
                        totalPrice: BigDecimal.valueOf(10L),
                        status: OrderStatus.CREATED,
                        createTime: LocalDateTime.of(2023, 8, 8, 11, 30, 0),
                        updateTime: LocalDateTime.of(2023, 8, 8, 11, 30, 0),
                        productDetails: productDetailList
                ),
        ]

        when:
        def result = orderDomainRepository.findByCustomerId(OrderFixture.CUSTOMER_ID)

        then:
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedOrder)
    }

    def "should return empty list given customer has no order"() {
        given:
        List<OrderPo> EMPTY_ORDER_PO_LIST = List.of()
        jpaOrderRepository.findByCustomerId(OrderFixture.CUSTOMER_ID) >> EMPTY_ORDER_PO_LIST

        List<Order> EMPTY_ORDER_LIST = List.of()

        when:
        def result = orderDomainRepository.findByCustomerId(OrderFixture.CUSTOMER_ID)

        then:
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(EMPTY_ORDER_LIST)
    }
}
