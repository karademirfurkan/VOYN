package com.furkankarademir.voyn.Classes;

import com.furkankarademir.voyn.ProfileClasses.Profile;

import java.io.Serializable;

public class User {
    private String name;
    private String surname;
    private String mail;
    private String password;

    private Profile userProfile;


    public User  (String name, String surname)
    {
        this.name = name;
        this.surname = surname;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile()
    {
        userProfile = new Profile(name, surname, mail,"department"); // replace "department" with the actual department
        return userProfile;
    }
}
