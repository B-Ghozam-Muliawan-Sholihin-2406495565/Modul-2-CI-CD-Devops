package id.ac.ui.cs.advprog.eshop.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import java.util.Iterator;

@Service
public class ProductFinderImpl implements ProductFinder{

    private final ProductRepository productRepository;

    public ProductFinderImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id);
    }
}
