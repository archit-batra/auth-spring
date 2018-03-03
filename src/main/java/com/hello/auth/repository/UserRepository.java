/*Spring Data JPA contains some built-in Repository implemented some common functions to work with database: findOne, findAll, 
  save,...
  The goal of Spring Data repository abstraction is to significantly reduce the amount of boilerplate code required to implement
  data access layers for various persistence stores.*/
package com.hello.auth.repository;

import com.hello.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {//repository: data service, <entity class,id of class>
	//use interface, spring data jpa provides class
    User findByUsername(String username);
    //dynamic finder method, finds a single user with the provided username 
}
