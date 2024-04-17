package com.test.telus.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.telus.app.entity.Product;
import com.test.telus.app.repository.ProductRepository;
import com.test.telus.app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return repository.findAllProducts();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> update(Integer id, Product product) {
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isPresent()) {
            Product productDb = productOptional.orElseThrow(null);
            
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());
            return Optional.of(repository.save(productDb));
            
        }
        return productOptional;
    }

    @Transactional
    @Override
    public Optional<Product> delete(Integer id) {
        Optional<Product> productOptional = repository.findById(id);
        productOptional.ifPresent(productDb -> {
            repository.delete(productDb);
        });
        return productOptional;
    }


}
