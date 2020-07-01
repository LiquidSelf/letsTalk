package app.services.users;

import app.database.dto.users.UsersDTO;
import base.DBTestDefContextConfig;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;

@RunWith(value = SpringRunner.class)
@ContextHierarchy({
        @ContextConfiguration(classes = DBTestDefContextConfig.class),
        @ContextConfiguration(classes = DB_DetailServiceTest_ContextConfig.class)
})
@Transactional
public class DB_DetailService_Test {

    @Autowired
    private DB_DetailService dao;
    @Autowired
    private ApplicationContext context;

    @Test
    public void testCreate_UserNameShouldNotBeNull() {
        UsersDTO user = new UsersDTO();

        user.setUsername(null);
        user.setPassword("pass");

        Assert.assertThrows(PersistenceException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                dao.createUser(user);
            }
        });
    }

//    @Test
//    public void testCreate_PasswordShouldNotBeNull() {
//        UsersDTO user = new UsersDTO();
//
//        user.setUsername("username");
//        user.setPassword(null);
//
//        Assert.assertThrows(PersistenceException.class, new ThrowingRunnable() {
//            @Override
//            public void run() throws Throwable {
//                dao.createUser(user);
//            }
//        });
//    }
}