package com.example.boravto.ui.classes;

public class Users{
    public String email, id, name, password, phone, role, city, land, img;

    public Users() {
    }

    public Users(String email, String id, String name, String password, String phone, String role, String city, String land, String img) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.city = city;
        this.land = land;
        this.img = img;
    }
}
