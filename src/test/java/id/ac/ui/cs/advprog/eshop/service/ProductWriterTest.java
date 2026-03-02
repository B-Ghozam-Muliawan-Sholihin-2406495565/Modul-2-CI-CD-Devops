package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepositoryImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class ProductWriterTest {

    @Mock
    private ProductRepositoryImpl productRepository;

    @InjectMocks
    private ProductWriter productWriter;

    @Test
    void testCreate() {
        Product product = new Product();
        productWriter.create(product);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testEdit() {
        Product product = new Product();
        productWriter.edit(product);
        verify(productRepository, times(1)).edit(product);
    }

    @Test
    void testDelete() {
        productWriter.delete("123");
        verify(productRepository, times(1)).delete("123");
    }
    
}
