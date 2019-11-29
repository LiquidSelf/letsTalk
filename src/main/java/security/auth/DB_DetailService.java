package security.auth;

import dao.users.Users;
import dao.users.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class DB_DetailService implements UserDetailsManager {

    @Autowired private UsersDao usersDao;
    @Autowired private PasswordEncoder passEncoder;

    @Override
    public void createUser(UserDetails user) {
        Users db_row = new Users();

        db_row.setUsername(user.getUsername());
        db_row.setPassword(passEncoder.encode(user.getPassword()));

        usersDao.save(db_row);
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
        Optional<Users> mayBeUser = usersDao.find(username);

        if(!mayBeUser.isPresent()) throw new UsernameNotFoundException("User ["+username+"] not found.");

        Users dbUser = mayBeUser.get();

        return User.builder()
                .username(dbUser.getUsername())
                .password(dbUser.getPassword())
                .roles("USER")
                .build();
    }
}
