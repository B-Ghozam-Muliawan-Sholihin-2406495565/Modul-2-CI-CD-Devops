package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.Iterator;

public interface ProductRepository extends BaseRepository<Product, String> {

    public Product create(Product product);
    public Iterator<Product> findAll();
    public Product findById(String id);
    public void edit(Product updatedProduct);
    public boolean delete(String id);
}