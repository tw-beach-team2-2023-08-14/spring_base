package com.example.domain.service

import com.example.common.exception.BusinessException
import com.example.domain.entity.Product
import com.example.domain.entity.ProductDetail
import com.example.domain.repository.ProductRepository
import com.example.presentation.vo.OrderProductReqDto
import com.example.presentation.vo.ProductStatus
import org.assertj.core.api.Assertions
import spock.lang.Specification

class OrderServiceTest extends Specification {

    ProductRepository productRepository = Mock()
    OrderService orderService = new OrderService(productRepository)

    def "should throw exception given invalid product in order request"() {
        given:
        Integer PRODUCT_ID = 11
        Long QUANTITY = 10L
        List<OrderProductReqDto> orderProducts = List.of(new OrderProductReqDto(PRODUCT_ID, QUANTITY))

        Product product = new Product(PRODUCT_ID, "testProduct", BigDecimal.TEN, ProductStatus.INVALID)
        productRepository.findById(PRODUCT_ID) >> product

        when:
        orderService.extractProductDetails(orderProducts)

        then:
        thrown(BusinessException)
    }

    def "should extract product details correctly"() {
        given:
        Integer PRODUCT_ID_ONE = 1
        Integer PRODUCT_ID_TWO = 2
        Long QUANTITY = 10L
        List<OrderProductReqDto> expectedProductDetails = List.of(
                new ProductDetail(PRODUCT_ID_ONE, "testProductOne", BigDecimal.TEN, QUANTITY),
                new ProductDetail(PRODUCT_ID_TWO, "testProductTwo", BigDecimal.TEN, QUANTITY)
        )
        List<OrderProductReqDto> orderProducts = List.of(
                new OrderProductReqDto(PRODUCT_ID_ONE, QUANTITY),
                new OrderProductReqDto(PRODUCT_ID_TWO, QUANTITY)
        )

        Product productOne = new Product(PRODUCT_ID_ONE, "testProductOne", BigDecimal.TEN, ProductStatus.VALID)
        productRepository.findById(PRODUCT_ID_ONE) >> productOne
        Product productTwo = new Product(PRODUCT_ID_TWO, "testProductTwo", BigDecimal.TEN, ProductStatus.VALID)
        productRepository.findById(PRODUCT_ID_TWO) >> productTwo

        when:
        List<ProductDetail> result = orderService.extractProductDetails(orderProducts)

        then:
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedProductDetails)
    }

}