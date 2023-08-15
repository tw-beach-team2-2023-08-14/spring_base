package com.example.domain.util


import com.example.domain.entity.ProductDetail
import com.example.presentation.vo.OrderProductReqDto
import org.assertj.core.api.Assertions
import spock.lang.Specification

class OrderUtilTest extends Specification {
    def "should calculate total price correctly"() {
        given:
        Integer PRODUCT_ID_ONE = 1
        Integer PRODUCT_ID_TWO = 2
        Long QUANTITY = 10L
        List<OrderProductReqDto> productDetails = List.of(
                new ProductDetail(PRODUCT_ID_ONE, "testProductOne", BigDecimal.TEN, QUANTITY),
                new ProductDetail(PRODUCT_ID_TWO, "testProductTwo", BigDecimal.TEN, QUANTITY)
        )


        when:
        BigDecimal result = OrderUtil.calculateTotalPrice(productDetails)

        then:
        Assertions.assertThat(result)
                .isEqualTo(BigDecimal.valueOf(200))
    }

}