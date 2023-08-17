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
                new Product(1, "book", BigDecimal.valueOf(10L), null, ProductStatus.VALID, 10),
                new Product(2, "book2", BigDecimal.valueOf(10L), BigDecimal.valueOf(0.9D), ProductStatus.INVALID, null),
                new Product(3, "book3", null, BigDecimal.valueOf(0.8D), ProductStatus.VALID, 0),
                new Product(4, "book4", BigDecimal.valueOf(10.27D), BigDecimal.valueOf(0.92D), ProductStatus.VALID, 10),
                new Product(5, "book5", BigDecimal.valueOf(0.01D), BigDecimal.valueOf(0.01D), ProductStatus.VALID, 10),
        ]

        productRepository.findAll() >> productList

        List<ProductDto> expectedProductList = [
                new ProductDto(id: 1, name: "book", price: BigDecimal.valueOf(10L), discount: BigDecimal.ONE, salePrice: new BigDecimal("10.00"), status: "VALID", inventory: 10),
                new ProductDto(id: 2, name: "book2", price: BigDecimal.valueOf(10L), discount: BigDecimal.valueOf(0.9D), salePrice: new BigDecimal("9.00"), status: "INVALID", inventory: null),
                new ProductDto(id: 3, name: "book3", price: null, discount: BigDecimal.valueOf(0.8D), salePrice: null, status: "VALID", inventory: 0),
                new ProductDto(id: 4, name: "book4", price: BigDecimal.valueOf(10.27D), discount: BigDecimal.valueOf(0.92D), salePrice: new BigDecimal("9.45"), status: "VALID", inventory: 10),
                new ProductDto(id: 5, name: "book5", price: BigDecimal.valueOf(0.01D), discount: BigDecimal.valueOf(0.01D), salePrice: new BigDecimal("0.01"), status: "VALID", inventory: 10),
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
