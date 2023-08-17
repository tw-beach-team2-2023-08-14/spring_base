package com.example.domain.factory

import com.example.common.exception.BusinessException
import com.example.domain.entity.Order
import com.example.domain.entity.OrderStatus
import com.example.domain.entity.Product
import com.example.domain.entity.ProductDetail
import com.example.domain.entity.ProductStatus
import org.assertj.core.api.Assertions
import org.dbunit.Assertion
import spock.lang.Specification

class OrderFactoryTest extends Specification {

    def "should calculate total price correctly"() {
        given:
        Integer PRODUCT_ID_ONE = 1
        Integer PRODUCT_ID_TWO = 2
        Long QUANTITY = 10L
        List<ProductDetail> productDetails = List.of(
                new ProductDetail(PRODUCT_ID_ONE, "testProductOne", BigDecimal.TEN, BigDecimal.valueOf(0.9D), QUANTITY),
                new ProductDetail(PRODUCT_ID_TWO, "testProductTwo", BigDecimal.TEN, BigDecimal.valueOf(0.9D), QUANTITY)
        )


        when:
        BigDecimal result = OrderFactory.calculateTotalPrice(productDetails)

        then:
        Assertions.assertThat(result)
                .isEqualTo(BigDecimal.valueOf(200))
    }

    def "should calculate sale total price correctly"() {
        given:
        Integer PRODUCT_ID_ONE = 1
        Integer PRODUCT_ID_TWO = 2
        Long QUANTITY = 10L
        List<ProductDetail> productDetails = List.of(
                new ProductDetail(PRODUCT_ID_ONE, "testProductOne", BigDecimal.TEN, BigDecimal.valueOf(9D), QUANTITY),
                new ProductDetail(PRODUCT_ID_TWO, "testProductTwo", BigDecimal.TEN, BigDecimal.valueOf(9D), QUANTITY)
        )


        when:
        BigDecimal result = OrderFactory.calculateSaleTotalPrice(productDetails)

        then:
        Assertions.assertThat(result)
                .isEqualTo(BigDecimal.valueOf(180D))
    }

    def "should extract product detail list correctly"() {
        given:
        Integer PRODUCT_ID_ONE = 1
        Long QUANTITY = 5L
        Product product = new Product(PRODUCT_ID_ONE, "testProductOne", BigDecimal.TEN, null, ProductStatus.VALID, 10)
        ProductDetail expectedResult = new ProductDetail(PRODUCT_ID_ONE, "testProductOne", BigDecimal.TEN, BigDecimal.TEN.setScale(4), QUANTITY)

        when:
        ProductDetail result = OrderFactory.extractProductDetailFromProduct(product, QUANTITY)

        then:
        Assertions.assertThat(result).isEqualTo(expectedResult)
    }

    def "should throw exception when extract product detail list and there is insufficient stock"() {
        given:
        Integer PRODUCT_ID_ONE = 1
        Long QUANTITY = 11L
        Product product = new Product(PRODUCT_ID_ONE, "testProductOne", BigDecimal.TEN, null, ProductStatus.VALID, 10)

        when:
        OrderFactory.extractProductDetailFromProduct(product, QUANTITY)

        then:
        thrown(BusinessException)
    }

    def "should create order successfully"() {
        given:
        Integer PRODUCT_ID_ONE = 1
        Integer PRODUCT_ID_TWO = 2
        Long QUANTITY = 10L
        List<ProductDetail> productDetails = List.of(
                new ProductDetail(PRODUCT_ID_ONE, "testProductOne", BigDecimal.TEN, BigDecimal.TEN, QUANTITY),
                new ProductDetail(PRODUCT_ID_TWO, "testProductTwo", BigDecimal.TEN, BigDecimal.TEN, QUANTITY)
        )
        Order expectedOrder = new Order(
                1,
                "dcabcfac-6b08-47cd-883a-76c5dc366d88",
                "orderId",
                BigDecimal.valueOf(200),
                OrderStatus.CREATED,
                null,
                null,
                productDetails
        )

        when:
        Order result = OrderFactory.createOrder(productDetails, "dcabcfac-6b08-47cd-883a-76c5dc366d88")

        then:
        Assertions.assertThat(result.getCustomerId()).isEqualTo(expectedOrder.getCustomerId())
        Assertions.assertThat(result.getTotalPrice()).isEqualTo(expectedOrder.getTotalPrice())
        Assertions.assertThat(result.getProductDetails()).isEqualTo(expectedOrder.getProductDetails())
    }
}