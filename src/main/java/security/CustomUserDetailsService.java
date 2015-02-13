package security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import enums.UserRole;
import pojo.AuthUser;
import repositories.AuthUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthUserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<UserRole> roles = user.getRoles();
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        for (UserRole role : roles) {
            authorityList.add(new SimpleGrantedAuthority(role.toString()));
        }
        org.springframework.security.core.userdetails.User authUser = new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorityList);
        return authUser;
    }

}
