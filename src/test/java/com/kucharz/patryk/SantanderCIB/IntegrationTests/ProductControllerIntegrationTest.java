package com.kucharz.patryk.SantanderCIB.IntegrationTests;

import com.kucharz.patryk.SantanderCIB.model.Product;
import com.kucharz.patryk.SantanderCIB.model.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class ProductControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void givenAProduct_whenAdding_shouldReturn201() {
        ProductDTO productDto = new ProductDTO("sku", "name", BigDecimal.valueOf(1), "productGroup", "uom");
        this.webTestClient
                .post()
                .uri("/items")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encode("USER:PASS".getBytes(UTF_8)))
                .body(Mono.just(productDto), ProductDTO.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void givenProductsWithDuplicatedSkus_whenAddingCollection_shouldThrowAnException() {
        ProductDTO productDto = new ProductDTO("sku", "name", BigDecimal.valueOf(1), "productGroup", "uom");
        ProductDTO productDto2 = new ProductDTO("sku", "name", BigDecimal.valueOf(1), "productGroup", "uom");
        this.webTestClient
                .post()
                .uri("/items/collections")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encode("USER:PASS".getBytes(UTF_8)))
                .body(Mono.just(Arrays.asList(productDto, productDto2)), List.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    public void givenDifferentProducts_whenFetchingFilterItems_shouldReturnItems() {
        ProductDTO productDto = new ProductDTO("sku", "name", BigDecimal.valueOf(1), "productGroup", "uom");
        ProductDTO productDto2 = new ProductDTO("sku2", "name2", BigDecimal.valueOf(1), "productGroup", "uom");
        this.webTestClient
                .post()
                .uri("/items")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encode("USER:PASS".getBytes(UTF_8)))
                .body(Mono.just(productDto), ProductDTO.class)
                .exchange()
                .expectStatus()
                .isCreated();

        this.webTestClient
                .post()
                .uri("/items")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encode("USER:PASS".getBytes(UTF_8)))
                .body(Mono.just(productDto2), ProductDTO.class)
                .exchange()
                .expectStatus()
                .isCreated();

        this.webTestClient
                .get()
                .uri("/items?page=0&size=1")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encode("USER:PASS".getBytes(UTF_8)))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Product.class)
                .hasSize(1);

        this.webTestClient
                .get()
                .uri("/items/filter?sku=sku2&name=&productGroup=")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encode("USER:PASS".getBytes(UTF_8)))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Product.class)
                .hasSize(1);

        this.webTestClient
                .get()
                .uri("/items/filter?sku=&name=&productGroup=productGroup")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encode("USER:PASS".getBytes(UTF_8)))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Product.class)
                .hasSize(2);
    }

}
