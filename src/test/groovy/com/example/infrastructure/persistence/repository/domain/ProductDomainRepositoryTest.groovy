package com.example.infrastructure.persistence.repository.domain

import com.example.domain.entity.Product
import com.example.infrastructure.persistence.entity.ProductPo
import com.example.infrastructure.persistence.repository.JpaProductRepository
import org.assertj.core.api.Assertions
import spock.lang.Specification

class ProductDomainRepositoryTest extends Specification {

    JpaProductRepository jpaProductRepository = Mock()
    ProductDomainRepository productDomainRepository = new ProductDomainRepository(jpaProductRepository)

    def "should return all products"() {
        given:
        List<ProductPo> jpaProducts = [
                new ProductPo(id: 1, name: "book", price: BigDecimal.valueOf(10L), status: "VALID"),
                new ProductPo(id: 2, name: "book2", price: BigDecimal.valueOf(10L), status: "INVALID"),
                new ProductPo(id: 3, name: "book2", price: null, status: "VALID"),
        ]

        jpaProductRepository.findAll() >> jpaProducts

        List<Product> expectedProducts = [
                new Product(id: 1, name: "book", price: BigDecimal.valueOf(10L), status: "VALID"),
                new Product(id: 2, name: "book2", price: BigDecimal.valueOf(10L), status: "INVALID"),
                new Product(id: 3, name: "book2", price: null, status: "VALID"),
        ]

        when:
        def result = productDomainRepository.findAll()

        then:
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedProducts)
    }
}
