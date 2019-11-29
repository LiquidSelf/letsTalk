package dao.users;

import com.sun.istack.NotNull;
import dao.Dao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.UsersValidation_Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersDao implements Dao<Users, String> {

    @Autowired
    private UsersValidation_Service validate;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Users> find(@NotNull String username) {

        Users user = sessionFactory.openSession().find(Users.class, username);
        return Optional.ofNullable(user);
    }

    @Override
    public List<Users> getAll() {
        return sessionFactory
                .openSession()
                .createQuery("from Users")
                .getResultList();
    }

    @Override
    public void save(Users user) {
        validate.assertUsersBase(user);
        sessionFactory.openStatelessSession().insert(user);
    }

    @Override
    public void update(Users user) {
        validate.assertUsersBase(user);
        sessionFactory.openSession().update(user);
    }

    @Override
    public void delete(Users user) {
        validate.assertUsersBase(user);
        sessionFactory.openSession().delete(user);
    }
}
