package com.devtalles.mokito_demo.service;

import com.devtalles.mokito_demo.model.Product;
import com.devtalles.mokito_demo.repository.IProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testFindProductByIdReturnsProductWhenFound() {

        // Arrrange (Preparacion)
        Long productId=1L;
        Product mockProduct = new Product(productId,"Laptop",120000);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act ( accion )
        Optional<Product> product = productService.findById(productId);

        // Assert
        assertTrue(product.isPresent());
        assertEquals(mockProduct, product.get());
        verify(productRepository, times(1)).findById(productId);
    }


    @Test
    void testFindProductByIdReturnsEmptyOptionalWhenNotFound() {

        // Arrrange (Preparacion)
        Long productId=99L;
        Product mockProduct = new Product(productId,"Laptop",120000);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act ( accion )
        Optional<Product> product = productService.findById(productId);

        // Assert
        assertFalse(product.isPresent());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetAllProdcutsReturnsListOfProducts() {
        // Arrrange (Preparacion)
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add( new Product(1L,"Laptop",120000));
        mockProducts.add( new Product(2L,"Xbox",190000));
        mockProducts.add( new Product(3L,"Keyboard",20000));
        when(productRepository.findAll()).thenReturn(mockProducts);

        // Act ( accion )
        List<Product> products = productService.findAll();

        // Assert
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(3, products.size());
        assertEquals("Laptop", products.get(0).getName());
        assertEquals("Xbox", products.get(1).getName());
        assertEquals("Keyboard", products.get(2).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testSaveProductSavesValidProduct(){

        // Arrange
        Product validProduct = new Product(null,"Monitor",5000);
        doNothing().when(productRepository).save(validProduct);

        // Action
        productService.save(validProduct);

        // Assert
        verify(productRepository, times(1)).save(validProduct);
    }

    @Test
    void testSaveProductThrowsExceptionWhenPriceIsZeroOrLess(){

        // Arrange
        Product invalidProduct = new Product(null,"Monitor",0);

        // Action & Assert
        assertThrows( IllegalArgumentException.class, ()-> {
            productService.save(invalidProduct);
        });

        verify(productRepository, never()).save(any(Product.class));
    }
}