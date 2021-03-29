package com.demo.jpa.service;

import com.demo.jpa.entity.Product;
import com.demo.jpa.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll(){
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public Optional<Product> getProductById(Long id){

        return productRepository.findById(id);
    }

    public Product create(Product product){

        return productRepository.save(product);
    }

    public Product update(Product product){
        return productRepository.save(product);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }

    public void deleteAll(){
        productRepository.deleteAll();
    }
}
