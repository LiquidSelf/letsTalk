package dao.users;

import com.sun.istack.NotNull;
import dao.Dao;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.internal.CriteriaQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UsersDao implements Dao<Users, String> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Users> find(@NotNull String username) {

        Users user = sessionFactory.openSession().find(Users.class, username);
        return Optional.ofNullable(sessionFactory.openSession().find(Users.class, username));
    }

    @Override
    public List<Users> getAll() {
        return sessionFactory.openSession().createQuery("from Users").getResultList();
    }

    @Override
    public void save(Users user) {
        assertName(user);
        sessionFactory.openSession().save(user);
    }

    @Override
    public void update(Users user) {
        assertName(user);
        sessionFactory.openSession().update(user);
    }

    @Override
    public void delete(Users user) {
        assertName(user);
        sessionFactory.openSession().delete(user);
    }

    private void assertName(@NotNull Users user){
        Assert.notNull(user.getName(), "username must not be empty.");
    }
}
