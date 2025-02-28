package com.hometest.controllers.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
@DiscriminatorValue("ADMIN")
public class Admin extends User{
    private String password;

    public Admin(String userName, String email, String password) {
        super(userName,email,Role.ADMIN);
        this.password = password;
    }

}
