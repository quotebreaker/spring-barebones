/**
 * 
 */
package controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import pojo.AuthUser;


/**
 * @author priyank
 *
 */
@Controller
public abstract class BaseController {
    
    @ModelAttribute("qbUser")
    protected AuthUser getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUser authUser = (AuthUser) auth.getPrincipal();         
        return authUser;
    }
    
}
