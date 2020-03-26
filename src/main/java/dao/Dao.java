package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID_TYPE> {

    T find(ID_TYPE id);

    List<T> getAll();

    T save(T t);

    T update(T t);

    void delete(T t);
}
