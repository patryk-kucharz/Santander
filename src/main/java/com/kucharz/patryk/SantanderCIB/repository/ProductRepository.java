package com.kucharz.patryk.SantanderCIB.repository;

import com.kucharz.patryk.SantanderCIB.model.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends CrudRepository<Product, String>, PagingAndSortingRepository<Product, String>, JpaSpecificationExecutor<Product> {
}
