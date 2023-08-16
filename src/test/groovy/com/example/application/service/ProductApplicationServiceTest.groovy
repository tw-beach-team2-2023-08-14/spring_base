package com.example.application.service

import com.example.domain.entity.Product
import com.example.domain.repository.ProductRepository
import com.example.presentation.vo.ProductDto
import com.example.domain.entity.ProductStatus
import org.assertj.core.api.Assertions
import spock.lang.Specification
import java.math.RoundingMode

class ProductApplicationServiceTest extends Specification {

    ProductRepository productRepository = Mock()
    ProductApplicationService productApplicationService = new ProductApplicationService(productRepository)

    def "should return all products"() {
        given:
        List<Product> productList = [
                new Product(1, "book", BigDecimal.valueOf(10L), null, ProductStatus.VALID),
                new Product(2, "book2", BigDecimal.valueOf(10L), BigDecimal.valueOf(0.9D), ProductStatus.INVALID),
                new Product(3, "book2", null, BigDecimal.valueOf(0.8D), ProductStatus.VALID),
        ]

        productRepository.findAll() >> productList

        List<ProductDto> expectedProductList = [
                new ProductDto(id: 1, name: "book", price: BigDecimal.valueOf(10L), discount: BigDecimal.ONE, salePrice: BigDecimal.valueOf(10).setScale(4, RoundingMode.HALF_UP), status: "VALID"),
                new ProductDto(id: 2, name: "book2", price: BigDecimal.valueOf(10L), discount: BigDecimal.valueOf(0.9D), salePrice: BigDecimal.valueOf(9).setScale(4, RoundingMode.HALF_UP), status: "INVALID"),
                new ProductDto(id: 3, name: "book2", price: null, discount: BigDecimal.valueOf(0.8D), salePrice: null, status: "VALID"),
        ]

        when:
        def result = productApplicationService.findAll()

        then:
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedProductList)
    }
}
