package com.demo.first.app;
// POJO --> Plain Old java objects (Ek esa class jo kisi bhi framework related class ko implement ya extend nhi karta h use kahte h POJO class)


public class User {
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

