package id.ac.ui.cs.advprog.eshop.service;

public interface BaseWriter<T, ID> {
    void edit(T entity);
    void delete(ID id);
    T create(T entity);
}
