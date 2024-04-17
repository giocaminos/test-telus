package com.test.telus.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.test.telus.app.entity.Product;

public interface ProductRepository  extends CrudRepository<Product, Integer> {

	@Query(value = "SELECT P FROM Product P",nativeQuery = false)
	public List<Product> findAllProducts();
}
