package dao.feed;

import dao.Dao;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FeedDao implements Dao<DB_FEED, Long> {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DB_FEED find(@NonNull Long id) {
        DB_FEED user = entityManager.find(DB_FEED.class, id);
        return user;
    }

    @Override
    public List<DB_FEED> getAll() {
        return entityManager
                .createQuery("from DB_FEED")
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(@NonNull DB_FEED user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(@NonNull DB_FEED user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NonNull DB_FEED user) {
        entityManager.remove(user);
    }
}
