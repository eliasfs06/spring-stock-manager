package com.eliasfs06.spring.stockmanager.repository;

import com.eliasfs06.spring.stockmanager.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends GenericRepository<Product> {
}
