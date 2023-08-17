package com.example.application.service

import com.example.common.exception.BusinessException
import com.example.domain.entity.*
import com.example.domain.repository.OrderRepository
import com.example.domain.repository.ProductRepository
import com.example.presentation.vo.OrderListDto
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
        String result = orderApplicationService.createOrder(orderReqDto)

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



    def "should retrieve order by consumer id"() {
        given:
        List<ProductDetail> productDetailList = [new ProductDetail(id: 1, name: "water", price: BigDecimal.valueOf(10L), quantity: 2)]

        List<Order> OrderDetails = [
                new Order(
                        id: 1,
                        customerId: "dcabcfac-6b08-47cd-883a-76c5dc366d88",
                        orderId: "order id",
                        primitiveTotalPrice: BigDecimal.valueOf(20L),
                        totalPrice: BigDecimal.valueOf(10L),
                        status: OrderStatus.CREATED,
                        createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        productDetails: productDetailList
                ),
        ]

        orderRepository.findByCustomerId(_) >> OrderDetails

        List<OrderProductDetailDto> orderProductDetails = [new OrderProductDetailDto(id: 1, name: "water", price: BigDecimal.valueOf(10L), quantity: 2)]
        List<OrderListDto> expectedOrderList = [
                new OrderListDto(
                        id: 1,
                        customerId: "dcabcfac-6b08-47cd-883a-76c5dc366d88",
                        orderId: "order id",
                        primitiveTotalPrice: BigDecimal.valueOf(20L),
                        totalPrice: BigDecimal.valueOf(10L),
                        createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        status: OrderStatus.CREATED,
                        productDetails: orderProductDetails
                ),
        ]

        when:
        def result = orderApplicationService.findOrderByCustomerIdAndOrderId("dcabcfac-6b08-47cd-883a-76c5dc366d88", null)

        then:
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedOrderList)
    }

    def "should filter order by order id and customer id when retrieve order"() {
        given:
        List<ProductDetail> productDetailList = [new ProductDetail(id: 1, name: "water", price: BigDecimal.valueOf(10L), quantity: 2)]

        List<Order> OrderDetails = [
                new Order(
                        id: 1,
                        customerId: "dcabcfac-6b08-47cd-883a-76c5dc366d88",
                        orderId: "orderId1",
                        primitiveTotalPrice: BigDecimal.valueOf(20L),
                        totalPrice: BigDecimal.valueOf(10L),
                        status: OrderStatus.CREATED,
                        createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        productDetails: productDetailList
                ),
                new Order(
                        id: 1,
                        customerId: "dcabcfac-6b08-47cd-883a-76c5dc366d88",
                        orderId: "orderId2",
                        primitiveTotalPrice: BigDecimal.valueOf(20L),
                        totalPrice: BigDecimal.valueOf(10L),
                        status: OrderStatus.CREATED,
                        createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        updateTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        productDetails: productDetailList
                ),
        ]

        orderRepository.findByCustomerId(_) >> OrderDetails

        List<OrderProductDetailDto> orderProductDetails = [new OrderProductDetailDto(id: 1, name: "water", price: BigDecimal.valueOf(10L), quantity: 2)]
        List<OrderListDto> expectedOrderList = [
                new OrderListDto(
                        id: 1,
                        customerId: "dcabcfac-6b08-47cd-883a-76c5dc366d88",
                        orderId: "orderId1",
                        primitiveTotalPrice: BigDecimal.valueOf(20L),
                        totalPrice: BigDecimal.valueOf(10L),
                        createTime: LocalDateTime.of(2023, 8, 8, 10, 30, 0),
                        status: OrderStatus.CREATED,
                        productDetails: orderProductDetails
                ),
        ]

        when:
        def result = orderApplicationService.findOrderByCustomerIdAndOrderId("dcabcfac-6b08-47cd-883a-76c5dc366d88", "orderId1")

        then:
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedOrderList)
    }
}
