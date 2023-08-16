package com.example.domain.factory

import com.example.domain.entity.ProductDetail
import org.assertj.core.api.Assertions
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
}
