package security.auth;

import dao.Dao;
import dao.users.DB_USER;
import dto.users.UsersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import services.JwtTokenUtil;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;

public class DB_DetailService implements UserDetailsManager {

    @Autowired private Dao<DB_USER, String> usersDao;
    @Autowired private PasswordEncoder passEncoder;
    @Autowired private JwtTokenUtil tokenUtil;
    @Autowired private HttpServletRequest request;

    @Override
    public void createUser(@NonNull UserDetails user) {

        if(user == null) throw new NullPointerException("UserDetails cannot be null");

        DB_USER check = usersDao.find(user.getUsername());
        if(check != null) throw new EntityExistsException(String.format("User [%s] exists", user.getUsername()));

        DB_USER newRow = new DB_USER();

        newRow.setUsername(user.getUsername());
        newRow.setPassword(passEncoder.encode(user.getPassword()));

        usersDao.save(newRow);
    }

    @Override
    public void updateUser(UserDetails user) {
        if(!(user instanceof UsersDTO)) throw new RuntimeException("unsupported user format");

        UsersDTO dto = (UsersDTO) user;
        DB_USER updateMe = findOrThrow(user.getUsername());

        updateMe.setAge(dto.getAge());
        usersDao.save(updateMe);
    }

    @Override
    public void deleteUser(@NonNull String username) {
        DB_USER lastBreath = findOrThrow(username);
        usersDao.delete(lastBreath);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        String token = tokenUtil.getTokenFromRequest(request);
        String username = tokenUtil.getUsernameFromToken(token);

        DB_USER updateMe = findOrThrow(username);

        if(!passEncoder.matches(oldPassword, updateMe.getPassword()))
            throw new BadCredentialsException(String.format("Password does not match for user [%s]", username));
        if(newPassword == null || newPassword.length() == 0) throw new BadCredentialsException("pass cant be empty");

        updateMe.setPassword(passEncoder.encode(newPassword));

        usersDao.update(updateMe);

    }

    @Override
    public boolean userExists(String username) {
        DB_USER mayBeUser = usersDao.find(username);
        if(mayBeUser == null)
        return true;
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DB_USER dbUser = findOrThrow(username);
        return new UsersDTO(dbUser);
    }

    private @NonNull
    DB_USER findOrThrow(@NonNull String usernama){
        DB_USER mayBeUser = usersDao.find(usernama);

        if(mayBeUser == null) throw new UsernameNotFoundException(String.format("User [%s] not found.", usernama));

        DB_USER user = mayBeUser;
        return user;
    }
}
