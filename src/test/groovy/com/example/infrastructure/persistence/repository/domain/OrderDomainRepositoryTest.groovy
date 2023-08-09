package com.example.infrastructure.persistence.repository.domain

import com.example.domain.entity.Order
import com.example.domain.entity.OrderItem
import com.example.domain.entity.OrderStatus
import com.example.infrastructure.persistence.entity.OrderPo
import com.example.infrastructure.persistence.repository.JpaOrderRepository
import org.assertj.core.api.Assertions
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import java.time.LocalDateTime

class OrderDomainRepositoryTest extends Specification {

    JpaOrderRepository jpaOrderRepository = Mock()
    OrderDomainRepository orderDomainRepository = new OrderDomainRepository(jpaOrderRepository)
    ObjectMapper objectMapper = new ObjectMapper()
//
//    def "should retrieve order list by customer id"() {
//        given:
//        List<OrderPo> jpaOrdersList = [
//                new OrderPo(
//                        id: 1,
//                        customerId: "dcabcfac-6b08-47cd-883a-76c5dc366d88",
//                        totalPrice: BigDecimal.valueOf(10L),
//                        status: OrderStatus.CREATED,
//                        createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
//                        updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
//                        productDetails: "product details"
//                ),
//                new OrderPo(
//                        id: 2,
//                        customerId: "dcabcfac-6b08-47cd-883a-76c5dc366d88",
//                        totalPrice: BigDecimal.valueOf(10L),
//                        status: OrderStatus.CREATED,
//                        createTime: LocalDateTime.of(2023, 8, 8, 11, 30, 0),
//                        updateTime: LocalDateTime.of(2023, 8, 8, 11, 30, 0),
//                        productDetails: "product details"
//                ),
//        ]
//
//        jpaOrderRepository.findByCustomerId("dcabcfac-6b08-47cd-883a-76c5dc366d88") >> jpaOrdersList
//
//        List<Order> expectedOrder = [
//                new Order(
//                        id: 1,
//                        customerId: "dcabcfac-6b08-47cd-883a-76c5dc366d88",
//                        totalPrice: BigDecimal.valueOf(10L),
//                        status: OrderStatus.CREATED,
//                        createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
//                        updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
//                        productDetails: "product details"
//                ),
//                new Order(
//                        id: 2,
//                        customerId: "dcabcfac-6b08-47cd-883a-76c5dc366d88",
//                        totalPrice: BigDecimal.valueOf(10L),
//                        status: OrderStatus.CREATED,
//                        createTime: LocalDateTime.of(2023, 8, 8, 11, 30, 0),
//                        updateTime: LocalDateTime.of(2023, 8, 8, 11, 30, 0),
//                        productDetails: "product details"
//                ),
//        ]
//
//        when:
//        def result = orderDomainRepository.findByCustomerId("dcabcfac-6b08-47cd-883a-76c5dc366d88")
//
//        then:
//        Assertions.assertThat(result)
//                .usingRecursiveComparison()
//                .ignoringCollectionOrder()
//                .isEqualTo(expectedOrder)
//
//    }

    def "Should return order Id"() {
        given:
        LocalDateTime createTime = LocalDateTime.now()
        LocalDateTime updateTime = LocalDateTime.now()
        Integer orderIdToCreate = Integer.valueOf(1)

        List<OrderItem> orderItems = List.of(new OrderItem("orderItemId1", "orderItemName1", BigDecimal.ONE, 1))

        String orderItemsToSave = objectMapper.writeValueAsString(orderItems)

        Order orderToSave = new Order(null, "consumerId", BigDecimal.ONE, OrderStatus.CREATED, createTime, updateTime, orderItems)

        OrderPo savedOrderPo = new OrderPo(Integer.valueOf(1), "consumerId", BigDecimal.ONE, OrderStatus.CREATED, createTime, updateTime, orderItemsToSave)

        jpaOrderRepository.save(_) >> savedOrderPo

        when:
        def orderId = orderDomainRepository.save(orderToSave)

        then:
        Assertions.assertThat(orderIdToCreate == orderId)
    }
}