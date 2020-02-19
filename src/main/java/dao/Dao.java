package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID_TYPE> {

    T find(ID_TYPE id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}
