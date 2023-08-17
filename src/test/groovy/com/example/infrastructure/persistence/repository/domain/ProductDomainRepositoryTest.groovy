package com.example.infrastructure.persistence.repository.domain


import com.example.common.exception.NotFoundException
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

    def "should return correct product"() {
        given:
        ProductPo jpaProduct = new ProductPo(id: 1, name: "book", price: BigDecimal.valueOf(10L), status: "VALID")

        jpaProductRepository.findAllById(List.of(1)) >> List.of(jpaProduct)

        Product expectedProduct = new Product(id: 1, name: "book", price: BigDecimal.valueOf(10L), status: "VALID")

        when:
        def result = productDomainRepository.findAllByIds(List.of(1))

        then:
        Assertions.assertThat(result.equals(expectedProduct))
    }


    def "should return empty list given not exist product id"() {
        given:
        jpaProductRepository.findAllById(List.of(2)) >> List.of()

        when:
        productDomainRepository.findAllByIds(List.of(2))

        then:
        thrown NotFoundException
    }


    def "should save product list successfully when update product inventory"() {
        given:
        List<ProductPo> productPoList = List.of(
                new ProductPo(id: 1, name: "book1", price: BigDecimal.valueOf(10L), status: "VALID", inventory:10),
                new ProductPo(id: 2, name: "book2", price: BigDecimal.valueOf(10L), status: "VALID", inventory:10000)
        )
        jpaProductRepository.saveAll(productPoList) >> productPoList

        List<Product> productList = List.of(
                new Product(id: 1, name: "book1", price: BigDecimal.valueOf(10L), status: "VALID", inventory:10),
                new Product(id: 2, name: "book2", price: BigDecimal.valueOf(10L), status: "VALID", inventory:10000)
        )

        when:
        def result = productDomainRepository.updateProductsInventory(productList)
        then:
        Assertions.assertThat(productList).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(result)

    }
}
