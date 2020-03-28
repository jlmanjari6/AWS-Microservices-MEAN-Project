package com.example.tourismapp.Models;

public class User {
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String age;

    public User(String firstName, String lastName, String gender, String email, String password, String age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return age;
    }
}
