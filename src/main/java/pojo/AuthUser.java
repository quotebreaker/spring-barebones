package pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import enums.SocialMediaService;
import enums.UserRole;


@Entity
@Table(name="auth_user")
public class AuthUser extends BaseData implements UserDetails {

    private static final long serialVersionUID = 8728659410248704423L;

    private String password;

    @Column(unique = true)
    private String email;
    
    @Column(unique = true)
    private String userName;

	private String fullName;
	
	@ElementCollection(fetch=FetchType.EAGER)
	List<UserRole> roles = new ArrayList<UserRole>();

    private boolean accountNonExpired = false;

    private boolean accountNonLocked = false;

    private boolean credentialsNonExpired = false;

    private boolean enabled = true;

//    private boolean autoCreated = false;
    
    public AuthUser() {
    }
    
    public AuthUser(AuthUser user) {
    	fullName = user.getFullName();
    	email = user.getEmail();
        password = user.getPassword();
        userName = user.getEmail();
        roles.addAll(user.getRoles());
        accountNonExpired = user.isAccountNonExpired();
        accountNonLocked = user.isAccountNonLocked();
        credentialsNonExpired = user.isCredentialsNonExpired();
        enabled = user.isEnabled();     
    }
    

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

	public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {        
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getUserName();
    }

}
