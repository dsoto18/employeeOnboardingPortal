package com.cooksys.groupfinal.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Data
public class Credentials {

//    @Column(nullable = false, unique = true)
    private String username;

//    @Column(nullable = false, unique = true)
//    private String email;

    @Column(nullable = false)
    private String password;

}
