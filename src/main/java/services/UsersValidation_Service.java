package services;

import dao.users.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UsersValidation_Service {

    public void assertUsersBase(Users user){
        Assert.notNull(user, "user is null");
        Assert.notNull(user.getUsername(), "username must not be empty.");
    }
    public void assertUsersBase(UserDetails user){
        Assert.notNull(user, "user is null");
        Assert.notNull(user.getUsername(), "username must not be empty.");
    }
}
