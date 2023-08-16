package com.example.domain.entity

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.*

class ProductTest extends Specification {


    def "should calculate sales price correctly when having legal price and discount"() {
        given:
        Product product = new Product(1, "newProduct", BigDecimal.valueOf(10), BigDecimal.valueOf(0.8), ProductStatus.VALID, 10)

        when:
        BigDecimal calculatedPrice = product.salePrice

        then:
        assertEquals(BigDecimal.valueOf(8).setScale(4), calculatedPrice)
    }

    def "should return null when having illegal price"() {
        given:
        Product product = new Product(1, "newProduct", null, BigDecimal.valueOf(0.8), ProductStatus.VALID, 10)

        when:
        BigDecimal calculatedPrice = product.salePrice

        then:
        assertEquals(null, calculatedPrice)
    }

    def "should return null when having illegal discount"() {
        given:
        Product product = new Product(1, "newProduct", BigDecimal.valueOf(10), null, ProductStatus.VALID, 10)

        when:
        BigDecimal calculatedPrice = product.salePrice

        then:
        assertEquals(BigDecimal.valueOf(10).setScale(4), calculatedPrice)
    }

    def "should return true when has sufficient stock"() {
        given:
        Product product = new Product(1, "newProduct", BigDecimal.valueOf(10), null, ProductStatus.VALID, 10)

        when:
        Boolean hasSufficientStock = product.hasSufficientInventory(5L)

        then:
        assertEquals(true, hasSufficientStock)
    }

    def "should return false when has insufficient stock"() {
        given:
        Product product = new Product(1, "newProduct", BigDecimal.valueOf(10), null, ProductStatus.VALID, 10)

        when:
        Boolean hasSufficientStock = product.hasSufficientInventory(15L)

        then:
        assertEquals(false, hasSufficientStock)
    }

    def "should return false when there is illegal inventory"() {
        given:
        Product product = new Product(1, "newProduct", BigDecimal.valueOf(10), null, ProductStatus.VALID, null)

        when:
        Boolean hasSufficientStock = product.hasSufficientInventory(5L)

        then:
        assertEquals(false, hasSufficientStock)
    }
}