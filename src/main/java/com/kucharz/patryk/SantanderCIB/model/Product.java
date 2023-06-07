package com.kucharz.patryk.SantanderCIB.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Product {
    @Id
    private String sku;
    private String name;

    private String productGroup;

    private String uom;
    private BigDecimal price;

    public Product() {

    }
}
