package com.kucharz.patryk.SantanderCIB.model;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDTO(@NotNull String sku, @NotNull String name, BigDecimal price, String productGroup, String uom) {
    public Product toProduct() {
        Product product = new Product();
        product.setSku(this.sku());
        product.setName(this.name());
        product.setPrice(this.price());
        product.setProductGroup(this.productGroup());
        product.setUom(this.uom());

        return product;
    }
}
