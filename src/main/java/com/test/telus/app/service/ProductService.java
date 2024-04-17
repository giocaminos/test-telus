package com.test.telus.app.service;

import java.util.List;
import java.util.Optional;

import com.test.telus.app.entity.Product;

public interface ProductService {

	List<Product> findAll();

    Optional<Product> findById(Integer id);

    Product save(Product product);
    
    Optional<Product> update(Integer id, Product product);

    Optional<Product> delete(Integer id);
}
