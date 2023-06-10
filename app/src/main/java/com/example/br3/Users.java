package com.example.br3;

public class Users {
    public String id,name, email, password, phone, city, role, img;

    public Users() {
    }

    public Users(String id, String name, String email, String password, String phone, String city, String role, String img) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.city = city;
        this.role = role;
        this.img = img;
    }
}
