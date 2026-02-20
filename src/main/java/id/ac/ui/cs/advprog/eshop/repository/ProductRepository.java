package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        Iterator<Product> productIterator = findAll();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        throw new NoSuchElementException("Product ID " + id + " not found");
    }
    

    public Product edit(Product updatedProduct) {
        Product existingProduct = findById(updatedProduct.getProductId());
        
        if (existingProduct != null) {
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setProductQuantity(updatedProduct.getProductQuantity());
            return existingProduct;
        }
        throw new NoSuchElementException("Product ID " + updatedProduct.getProductId() + " not found");
    }

    public boolean delete(String id) {
        return productData.removeIf(product -> product.getProductId().equals(id));
    }  
}