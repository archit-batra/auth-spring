package com.hello.auth.model;

import javax.persistence.*;//use jpa annotation not hibernate as it is sun java std, if hibernate becomes obsolete & some other specification other than hibernate becomes popular/better then we have to change the import 
import java.util.Set;
//java class
//java bean : pvt data members, data accessed via getters & setters, no args constructor
@Entity//create table out of this class
@Table(name = "user")//if table name different than class
public class User {
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Role> roles;

    @Id //primary key column
    @GeneratedValue(strategy = GenerationType.AUTO) //auto generate prim key //auto=no sequence
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient //not to store password in session/database column
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany //join table required,multi users have multi roles 
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    //third table let name be "
    //joinColumns 		 : foreign key to the source object's primary key
    //inverseJoinColumns : foreign key to the target object's primary key
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
