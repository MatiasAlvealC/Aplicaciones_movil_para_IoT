package com.example.proyecto_firebase;

public class HelperClass {
    String name, email, password, username;

    public HelperClass(String name, String email, String username,  String password) {
        this.setName(name);
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public HelperClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
