package com.example.application.service

import com.example.common.exception.BusinessException
import com.example.domain.entity.*
import com.example.domain.repository.OrderRepository
import com.example.domain.repository.ProductRepository
import com.example.fixture.OrderFixture
import com.example.presentation.vo.OrderDto
import com.example.presentation.vo.OrderProductDetailDto
import com.example.presentation.vo.OrderProductReqDto
import com.example.presentation.vo.OrderReqDto
import org.assertj.core.api.Assertions
import spock.lang.Specification

import java.time.LocalDateTime

class OrderApplicationServiceTest extends Specification {

    ProductRepository productRepository = Mock()
    OrderRepository orderRepository = Mock()
    OrderApplicationService orderApplicationService = new OrderApplicationService(orderRepository, productRepository)

    def "should save order and return correct order id"() {
        given:
        Integer PRODUCT_ID = 11
        String ORDER_ID = UUID.randomUUID().toString()
        OrderCreation newOrder = new OrderCreation(ORDER_ID)
        Integer QUANTITY = 10

        List<OrderProductReqDto> orderProducts = List.of(new OrderProductReqDto(PRODUCT_ID, QUANTITY))
        OrderReqDto orderReqDto = new OrderReqDto("customerId", orderProducts)

        Product product = new Product(PRODUCT_ID, "testProduct", BigDecimal.TEN, null, ProductStatus.VALID, 10)
        productRepository.findAllByIds(List.of(PRODUCT_ID)) >> List.of(product)

        List<Product> productListToUpdate = List.of(new Product(PRODUCT_ID, "testProduct", BigDecimal.TEN, null, ProductStatus.VALID, 0))

        orderRepository.save(_) >> newOrder

        when:
        OrderCreation result = orderApplicationService.createOrder(orderReqDto)

        then:
        Assertions.assertThat(result.equals(newOrder))
        1 * productRepository.updateProductsInventory(productListToUpdate)
    }

    def "should throw exception given invalid product in order request"() {
        given:
        Integer PRODUCT_ID = 11
        String ORDER_ID = UUID.randomUUID().toString()
        OrderCreation newOrder = new OrderCreation(ORDER_ID)
        Integer QUANTITY = 10

        List<OrderProductReqDto> orderProducts = List.of(new OrderProductReqDto(PRODUCT_ID, QUANTITY))
        OrderReqDto orderReqDto = new OrderReqDto("customerId", orderProducts)

        Product product = new Product(PRODUCT_ID, "testProduct", BigDecimal.TEN, null, ProductStatus.INVALID, null)
        productRepository.findAllByIds(List.of(PRODUCT_ID)) >> List.of(product)

        orderRepository.save(_) >> newOrder

        when:
        orderApplicationService.createOrder(orderReqDto)

        then:
        0 * orderRepository.save(_)
        thrown(BusinessException)
    }

    def "should return order by order id and customer id when retrieve order"() {
        given:

        Order order = OrderFixture.ORDER_ONE
        orderRepository.findByCustomerIdAndOrderId(OrderFixture.CUSTOMER_ID,OrderFixture.ORDER_ID_ONE) >> order

        OrderDto expectedOrderDto = OrderFixture.ORDER_DTO_ONE

        when:
        def result = orderApplicationService.findOrderByCustomerIdAndOrderId(OrderFixture.CUSTOMER_ID, OrderFixture.ORDER_ID_ONE)

        then:
        Assertions.assertThat(result).isEqualTo(expectedOrderDto)
    }

    def "should return order list given customer id"() {
        given:
        List<Order> OrderList = List.of(OrderFixture.ORDER_ONE, OrderFixture.ORDER_TWO)

        orderRepository.findByCustomerId(OrderFixture.CUSTOMER_ID) >> OrderList

        List<OrderDto> expectedOrderList = List.of(OrderFixture.ORDER_DTO_ONE, OrderFixture.ORDER_DTO_TWO)

        when:
        def result = orderApplicationService.findOrderByCustomerId(OrderFixture.CUSTOMER_ID)

        then:
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedOrderList)
    }

    def "should cancel order successfully when order fetched with valid status"() {
        given:
        Integer PRODUCT_ID = 11
        def productDetailList =
                List.of(
                        OrderFixture.productDetailBuilder()
                                .id(PRODUCT_ID)
                                .quantity(2)
                                .build()
                )

        def order = OrderFixture
                .orderBuilder()
                .productDetails(productDetailList)
                .build()

        def cancelledOrder = OrderFixture.orderBuilder()
                .productDetails(productDetailList)
                .status(OrderStatus.CANCELLED)
                .build()

        orderRepository.lockAndFindByOrderId(_) >> order

        def productList = List.of(OrderFixture
                .productBuilder()
                .id(PRODUCT_ID)
                .inventory(10).build())
        productRepository.lockAndFindAllByIds(_) >> productList

        def updatedProductList = List.of(OrderFixture
                .productBuilder()
                .id(PRODUCT_ID)
                .inventory(12)
                .build())

        when:
        orderApplicationService.cancelOrder(OrderFixture.CUSTOMER_ID, OrderFixture.ORDER_ID_ONE)

        then:
        1 * orderRepository.save(cancelledOrder)
        1 * productRepository.updateProductsInventory(updatedProductList)
    }

}
