package com.furkankarademir.voyn.ProfileClasses;

import java.io.Serializable;

public class Profile implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String department;

    public Profile(String name, String surname, String email, String department) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.department = department;
    }


}
