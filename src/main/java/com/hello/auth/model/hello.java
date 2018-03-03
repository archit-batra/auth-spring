package com.hello.auth.model;

import javax.persistence.*;//use jpa annotation not hibernate as it is sun java std, if hibernate becomes obsolete & some other specification other than hibernate becomes popular/better then we have to change the import 
//java class
//java bean : pvt data members, data accessed via getters & setters, no args constructor
@Entity//create table out of this class
public class hello {
    private Long id;
    private String tweet;
    
    @Id //primary key column
    @GeneratedValue(strategy = GenerationType.AUTO) //auto generate prim key //auto=no sequence
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String username) {
        this.tweet = tweet;
    }
    }
