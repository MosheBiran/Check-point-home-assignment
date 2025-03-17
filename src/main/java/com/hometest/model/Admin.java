package com.hometest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
