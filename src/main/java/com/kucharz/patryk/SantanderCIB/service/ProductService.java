package com.kucharz.patryk.SantanderCIB.service;

import com.kucharz.patryk.SantanderCIB.model.Product;
import com.kucharz.patryk.SantanderCIB.model.ProductDTO;
import com.kucharz.patryk.SantanderCIB.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(ProductDTO productDTO) {
        if(productRepository.existsById(productDTO.sku())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already exists");
        }

        productRepository.save(productDTO.toProduct());
    }

    public void addMultipleProducts(Collection<ProductDTO> productDTOCollection) {
        List<String> skus = productDTOCollection.stream()
                .map(ProductDTO::sku)
                .collect(Collectors.toList());
        boolean hasNoDuplicates = skus.stream()
                .filter(sku -> Collections.frequency(skus, sku) > 1)
                .collect(Collectors.toSet()).isEmpty();
        if(!hasNoDuplicates) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Duplicated SKUs");
        }

        List<Product> products = productDTOCollection.stream().map(ProductDTO::toProduct).collect(Collectors.toList());
        //TODO check if already exists or duplicates
        productRepository.saveAll(products);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> findFilteredProducts(String sku, String name, String productGroup) {
        List<Specification<Product>> specifications = new LinkedList<>();
        if(Objects.nonNull(sku) && sku != "") {
            specifications.add((product, cq, cb) -> cb.equal(product.get("sku"), sku));
        }

        if(Objects.nonNull(name) && name != "") {
            specifications.add((product, cq, cb) -> cb.equal(product.get("name"), name));
        }

        if(Objects.nonNull(productGroup) && productGroup != "") {
            specifications.add((product, cq, cb) -> cb.equal(product.get("productGroup"), productGroup));
        }
        return productRepository.findAll(Specification.allOf(specifications));
    }

    public void delete(String sku) {
         productRepository.deleteById(sku);
    }

    public void update(ProductDTO productDTO) {
        productRepository.save(productDTO.toProduct());
    }
}
