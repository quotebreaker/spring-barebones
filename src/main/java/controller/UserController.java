package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pojo.AuthUser;
import service.EmailService;
import service.UserService;
import enums.UserRole;
import exception.UserRegistrationException;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	EmailService emailService;
	
    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;	
	
    @RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
    public String register(@ModelAttribute AuthUser user, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        String ret = "user/register";
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            List<UserRole> roles = new ArrayList<UserRole>();
            roles.add(UserRole.ROLE_USER);
            try{
            	userService.createUser(user, roles);
                boolean loginSuccess = authenticateUserAndSetSession(user, request);

                if (loginSuccess) {
                    try {
                        authenticationSuccessHandler.onAuthenticationSuccess(request, response, SecurityContextHolder.getContext().getAuthentication());
                        emailService.sendUserSignupEmail(user);
                    } catch (ServletException e) {
                        loginSuccess = false;
                    }
                }
                if (!loginSuccess) {
                    response.sendRedirect("/user/accessdenied");
                }
            }
            catch(UserRegistrationException ex){
            	if(ex.getExceptionType() == UserRegistrationException.Exceptiontype.EXISTING_USER){
            		model.addAttribute("dangerMessage", ex.getMessage());
            	}
            }
        }
        return ret;
    }
    
    private boolean authenticateUserAndSetSession(AuthUser user, HttpServletRequest request) {
        boolean ret = false;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        if (authenticatedUser != null) {
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            ret = true;
        }
        return ret;
    } 
    
    @RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
    public String login(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {
        return "user/login";
    }

}
