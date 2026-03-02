package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepositoryImpl;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class ProductFinderTest {

    @Mock
    private ProductRepositoryImpl productRepository;

    @InjectMocks
    private ProductFinderImpl productFinder;
    

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productFinder.findAll();
        
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Product product = new Product();
        when(productRepository.findById("123")).thenReturn(product);
        
        Product result = productFinder.findById("123");
        
        assertEquals(product, result);
    }

}
