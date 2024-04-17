package com.test.telus.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.telus.app.entity.Product;
import com.test.telus.app.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> list() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Integer id) {
    	
        Optional<Product> productOptional = service.findById(id);
        return validationCRUD(productOptional);
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {
    	 if (result.hasFieldErrors()) {
             return validation(result);
         }
    	 Map<String,Object> status = new HashMap<>();
    	 status.put("description", HttpStatus.CREATED.getReasonPhrase());
    	 status.put("status", HttpStatus.CREATED.value());
         status.put("product", service.save(product));
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Integer id) {
    	if (result.hasFieldErrors()) {
            return validation(result);
        }
        Optional<Product> productOptional = service.update(id, product);
        return validationCRUD(productOptional);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Product> productOptional = service.delete(id);       
        return validationCRUD(productOptional);
    }
    
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("description", HttpStatus.BAD_REQUEST.getReasonPhrase());
        
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "The field " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
    
    private ResponseEntity<?> validationCRUD(Optional<Product> productOptional){
    	Map<String,Object> status = new HashMap<>();
    	if (productOptional.isPresent()) {
       	 status.put("status", HttpStatus.OK.value());
            status.put("description", HttpStatus.OK.getReasonPhrase());
            status.put("product", productOptional.orElseThrow(null));
           return ResponseEntity.ok(status);
       }
       status.put("description", HttpStatus.NOT_FOUND.getReasonPhrase());
       status.put("status", HttpStatus.NOT_FOUND.value());
       
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status);
    }
}

