package com.kucharz.patryk.SantanderCIB.IntegrationTests;

import com.kucharz.patryk.SantanderCIB.model.Product;
import com.kucharz.patryk.SantanderCIB.model.ProductDTO;
import com.kucharz.patryk.SantanderCIB.repository.ProductRepository;
import com.kucharz.patryk.SantanderCIB.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceIntegrationTest {

	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@BeforeEach
	void setupService() {
		productService = new ProductService(productRepository);
	}

	@Test
	public void givenAProduct_whenAddingProduct_ShouldSaveToDB() {
		ProductDTO productDTO = new ProductDTO("sku", "name", BigDecimal.valueOf(1.5), "productGroup", "uom");

		productService.addProduct(productDTO);

		Optional<Product> sku = productRepository.findById("sku");
		assertThat(sku.isPresent()).isTrue();
		assertThat(sku.get().getName()).isEqualTo("name");
		//....
	}

}
