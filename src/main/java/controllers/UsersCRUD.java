package controllers;

import dao.Dao;
import dao.users.DB_USER;
import dto.DTO;
import dto.users.UsersDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import services.JwtTokenUtil;

@RestController
@RequestMapping("/api/users")
public class UsersCRUD {

    @Autowired private Dao<DB_USER, String> dao;
    @Autowired private UserDetailsManager userDetailsManager;
    @Autowired private JwtTokenUtil tokenUtil;

    private static final Log logger = LogFactory.getLog(UsersCRUD.class);

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@RequestBody UsersDTO dto) {
        if(dto == null || dto.getUsername() == null)
        return new ResponseEntity(new RuntimeException("cannot update user, empty request"), HttpStatus.BAD_REQUEST);

        try {
            userDetailsManager.updateUser(dto);
            String newToken = tokenUtil.generateToken(dto);

            return ResponseEntity.ok(DTO.mk(newToken));
        }catch (Exception ex){
            logger.error(ex);
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

}
