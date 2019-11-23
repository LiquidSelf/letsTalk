package security.auth;

import dao.users.Users;
import dao.users.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class DB_DetailService implements UserDetailsManager {

    @Autowired UsersDao usersDao;

    @Override
    public void createUser(UserDetails user) {
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> isIt = usersDao.find(username);

        if(!isIt.isPresent()) throw new UsernameNotFoundException("User ["+username+"] not found.");

        Users dbUser = isIt.get();

        return User.builder()
                .username(dbUser.getName())
                .password(dbUser.getPassword())
                .roles("USER")
                .build();
    }
}
