package dao.users;

import dao.Dao;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UsersDao implements Dao<DB_USER, String> {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DB_USER find(@NonNull String username) {
        DB_USER user = entityManager.find(DB_USER.class, username);
        return user;
    }

    @Override
    public List<DB_USER> getAll() {
        return entityManager
                .createQuery("from Users")
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(@NonNull DB_USER user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(@NonNull DB_USER user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NonNull DB_USER user) {
        entityManager.remove(user);
    }
}
