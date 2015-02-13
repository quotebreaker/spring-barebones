package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pojo.AuthUser;
import repositories.AuthUserRepository;
import enums.UserRole;
import exception.UserRegistrationException;

@Service
public class UserService {
	
	@Autowired
	private AuthUserRepository authUserRepository;
	
    @Transactional
    public AuthUser createUser(AuthUser authUser,List<UserRole> roles) throws UserRegistrationException {
        AuthUser user = new AuthUser(authUser);
        authUser.setUserName(authUser.getEmail());
        user.setPassword(getHashedPassword(authUser.getPassword()));
        user.setUserName(authUser.getEmail());
        user.setRoles(roles);
        try{
        	authUserRepository.saveAndFlush(user);
        }
        catch(org.hibernate.exception.ConstraintViolationException ex){
        	throw new UserRegistrationException("Email already exists", UserRegistrationException.Exceptiontype.EXISTING_USER);
        }
        return user;
    }
    
    public String getHashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
