package com.furkankarademir.voyn.ProfileClasses;

public class Profile {
    private String name;
    private String surname;
    private String email;
    private String department;
    private String age;



    public Profile(String name, String surname, String email, String department, String age) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.department = department;
        this.age = age;
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
