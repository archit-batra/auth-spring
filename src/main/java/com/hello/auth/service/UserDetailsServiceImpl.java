/*To implement login/authentication with Spring Security, we need to implement org.springframework.security.core.userdetails.
  UserDetailsService interface*/
package com.hello.auth.service;

import com.hello.auth.model.Role;
import com.hello.auth.model.User;
import com.hello.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
/*Core Components
SecurityContext
SecurityContextHolder
Authentication
UserDetailsService
GrantedAuthority*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
  /*The transactional annotation itself defines the scope of a single database transaction. The database transaction happens
    inside the scope of a persistence context.
    The persistence context is in JPA the EntityManager, implemented internally using an Hibernate Session (when using Hibernate as the persistence provider).
JPA and Transaction Management
It’s important to notice that JPA on itself does not provide any type of declarative transaction management. When using JPA outside of a dependency injection container, transactions need to be handled programatically by the developer:
UserTransaction utx = entityManager.getTransaction(); 
try { 
    utx.begin(); 
    businessLogic();
    utx.commit(); 
} catch(Exception ex) { 
    utx.rollback(); 
    throw ex; 
}
This way of managing transactions makes the scope of the transaction very clear in the code, but it has several disavantages:
it’s repetitive and error prone
any error can have a very high impact
errors are hard to debug and reproduce
this decreases the readability of the code base
What if this method calls another transactional method?
Using Spring @Transactional
With Spring @Transactional, the above code gets reduced to simply this:
@Transactional
public void businessLogic() {
    ... use entity manager inside a transaction ...
}
This is much more convenient and readable, and is currently the recommended way to handle transactions in Spring.
By using @Transactional, many important aspects such as transaction propagation are handled automatically. In this case if another transactional method is called by businessLogic(), that method will have the option of joining the ongoing transaction.
One potential downside is that this powerful mechanism hides what is going on under the hood, making it hard to debug when things don’t work.
*/
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
  /*to get the list of granted authorities for the logged in user: This comes as part of the authorization process.
    This is retrieved by calling the getAuthorities() in Authentication object.
    This returns the list of GrantedAuthority which denotes roles for the users.*/  
    }
}
