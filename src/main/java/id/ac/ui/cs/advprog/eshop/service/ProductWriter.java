package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductWriter extends BaseWriter<Product, String>{
    public Product create (Product product);
    public void edit(Product product);
    public void delete(String id);
}
