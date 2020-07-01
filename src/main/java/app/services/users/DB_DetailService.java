package app.services.users;

import app.database.dao.Dao;
import app.database.dao.users.DB_USER;
import app.database.dto.users.UsersDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import app.services.JwtTokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Component
@Validated
public class DB_DetailService implements UserDetailsManager {

    @Autowired private Dao<DB_USER, String> usersDao;
    @Autowired private PasswordEncoder passEncoder;
    @Autowired private JwtTokenUtil tokenUtil;
    @Autowired private HttpServletRequest request;
    @Autowired private ModelMapper modelMapper;

    @Override
    public void createUser(@Valid UserDetails user) {

        try {

            DB_USER newRow = new DB_USER();

            newRow.setUsername(user.getUsername());
            newRow.setPassword(user.getPassword());

            usersDao.save(newRow);

        }catch (EntityExistsException exception){
            throw new EntityExistsException(String.format("User [%s] exists", user.getUsername()));
        }
    }

    @Override
    public void updateUser(@Valid UserDetails user) {
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

        usersDao.saveOrUpdate(updateMe);

    }

    @Override
    public boolean userExists(String username) {
        DB_USER mayBeUser = usersDao.find(username);
        if(mayBeUser == null)
        return true;
        return false;
    }

    @Override
    public UsersDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        DB_USER dbUser = findOrThrow(username);
        return modelMapper.map(dbUser, UsersDTO.class);
    }

    private @NonNull
    DB_USER findOrThrow(@NonNull String usernama){
        DB_USER mayBeUser = usersDao.find(usernama);

        if(mayBeUser == null) throw new UsernameNotFoundException(String.format("User [%s] not found.", usernama));

        DB_USER user = mayBeUser;
        return user;
    }
}
