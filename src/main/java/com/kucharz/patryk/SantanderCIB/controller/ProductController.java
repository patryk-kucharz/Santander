package com.kucharz.patryk.SantanderCIB.controller;

import com.kucharz.patryk.SantanderCIB.model.Product;
import com.kucharz.patryk.SantanderCIB.model.ProductDTO;
import com.kucharz.patryk.SantanderCIB.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/items")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(params = { "page", "size" })
    public Iterable<Product> getAllProducts(@RequestParam("page") int page, @RequestParam("size") int size) {
        return productService.findAll(Pageable.ofSize(size).withPage(page));
    }

    @GetMapping(path = "/filter", params = { "sku", "name", "productGroup" })
    public Iterable<Product> getFilteredProducts(@RequestParam("sku") String sku, @RequestParam("name") String name, @RequestParam("productGroup") String productGroup) {
        return productService.findFilteredProducts(sku, name, productGroup);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createItem(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
    }

    @PostMapping("/collections")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createItems(@RequestBody Collection<ProductDTO> list) {
        productService.addMultipleProducts(list);
    }

    @DeleteMapping("/{sku}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteItem(@PathVariable String sku) {
        productService.delete(sku);
    }

    @PutMapping("/{sku}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateItem(@RequestBody ProductDTO productDTO) {
        productService.update(productDTO);
    }

}
