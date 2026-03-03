package id.ac.ui.cs.advprog.eshop.repository;
import java.util.Iterator;

public interface BaseRepository<T, ID> {
    T create(T entity);
    Iterator<T> findAll();
    T findById(ID id);
    void edit(T entity);
    boolean delete(ID id);
}


