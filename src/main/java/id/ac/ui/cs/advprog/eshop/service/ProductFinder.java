package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductFinder {
    public List<Product> findAll();
    public Product findById(String id);
}
