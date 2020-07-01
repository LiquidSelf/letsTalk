package app.database.dao;

import java.util.List;

public interface Dao<T, ID_TYPE> {

    T find(ID_TYPE id);

    List<T> getAll();

    void save(T t);

    T saveOrUpdate(T t);

    void delete(T t);
}
