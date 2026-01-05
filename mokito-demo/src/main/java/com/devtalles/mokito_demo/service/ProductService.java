package com.devtalles.mokito_demo.service;

import com.devtalles.mokito_demo.model.Product;
import com.devtalles.mokito_demo.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;


    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public void save(Product product) {
        if(product.getPrice()<=0){
            throw new IllegalArgumentException("El precio del producto debe ser mayor a cero");
        }
        productRepository.save(product);
    }
}
