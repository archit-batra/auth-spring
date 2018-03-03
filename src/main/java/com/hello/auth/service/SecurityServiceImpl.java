/*We create SecurityService to provide current loggedin user and auto login user after registering an account.*/
package com.hello.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        /*SecurityContext is the interface which is the corner stone of storing all the security related details for our
          application. 
          When we enable spring security for our application, a SecurityContext will enable for each application
          and stores the details of authenticated user, etc. 
          It uses Authentication object for storing the details related to authentications.
          SecurityContextHolder is the class which is important for accessing any value from the SecurityContext. 
          We would never directly access the security context, we have to use SecurityContextHolder to get the context and
          then access the details. 
          In simple terms, it is an interface between client and context.
          By default, this SecurityContextHolder uses ThreadLocal for storing the details.*/
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }

        return null;
    }

    @Override
    public void autologin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      /*UserDetailsService is a core interface in spring security to load user specific data. 
        This interface is considered as user DAO and will be implemented by specific DAO implementations. 
        For example, for a basic in memory authentication, there is a InMemoryUserDetailsManager. 
        This interface declares only one method loadUserByUsername(String username) which simplifies the implementation classes
        to write other specific methods.
		In simple words, if we want to use our existing DAO classes to load the user details from the database, just implement
		the UserDetailsService and override the method loadUserByUsername(String username).*/  
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
      /*For a simple user name and password authentication, spring security would use UsernamePasswordAuthenticationToken. 
        When user enters username and password, system creates a new instance of UsernamePasswordAuthenticationToken.*/
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
      /*The above token will be passed to AuthenticationManager for the validation. 
        Internally what AuthenticationManager will do is to iterate the list of configured AuthenticationProvider to validate the
        request. 
        There should be atleast one provider to be configured for the valid authentication.
		If the above step is success, the AuthenticationManager returns a populated Authentication instance.*/  
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      /*The final step is to establish a security context by invoking SecurityContextHolder.getContext().setAuthentication(…​),
        passing in the returned authentication object.*/
            logger.debug(String.format("Auto login %s successfully!", username));
        }
    }
}
