package app.database.dto.users;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.testng.AssertJUnit.*;

@RunWith(value = SpringRunner.class)
public class UsersDTOTest {

    private static Validator validator;

    @BeforeClass
    public static void init(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void emptyUsernameNotAllowed(){
        UsersDTO user = new UsersDTO();

        user.setUsername(null);
        user.setPassword("pass");

        Set<ConstraintViolation<UsersDTO>> violations = validator.validate(user);

        assertEquals(violations.size(), 1);
    }

    @Test
    public void emptyPasswordNotAllowed(){
        UsersDTO user = new UsersDTO();

        user.setUsername("username");
        user.setPassword(null);

        Set<ConstraintViolation<UsersDTO>> violations = validator.validate(user);

        assertEquals(violations.size(), 1);
    }
    @Test
    public void userNameLengthShouldBeIn20() {
        UsersDTO user = new UsersDTO();

        user.setUsername(StringUtils.repeat("a", 21));
        user.setPassword("password");

        Set<ConstraintViolation<UsersDTO>> violations = validator.validate(user);

        assertEquals(violations.size(), 1);
    }


    @Test
    public void userPasswordLengthShouldBeIn30() {
        UsersDTO user = new UsersDTO();

        user.setUsername("username");
        user.setPassword(StringUtils.repeat("a", 31));

        Set<ConstraintViolation<UsersDTO>> violations = validator.validate(user);

        assertEquals(violations.size(), 1);
    }
}