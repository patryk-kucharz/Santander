package com.kucharz.patryk.SantanderCIB.UnitTests;

import com.kucharz.patryk.SantanderCIB.model.ProductDTO;
import com.kucharz.patryk.SantanderCIB.repository.ProductRepository;
import com.kucharz.patryk.SantanderCIB.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductServiceUnitTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    void setupService() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void givenAListOfProductsWithDuplicatedSku_whenAddingThem_ShouldThrowException() {
        List<ProductDTO> productDTOS = List.of(
                new ProductDTO("sku", "name", BigDecimal.valueOf(1), "productGroup", "uom"),
                new ProductDTO("sku2", "name2", BigDecimal.valueOf(1), "productGroup", "uom"),
                new ProductDTO("sku", "name2", BigDecimal.valueOf(1), "productGroup", "uom")
        );


        assertThrows(ResponseStatusException.class, () -> productService.addMultipleProducts(productDTOS));
    }

}
