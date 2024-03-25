package com.furkankarademir.voyn.ProfileClasses;

import java.io.Serializable;

public class Profile {
    private String name;
    private String surname;
    private String email;
    private String department;

    public Profile(String name, String surname, String email, String department) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDepartment(String department) {
        this.department = department;
    }



}
