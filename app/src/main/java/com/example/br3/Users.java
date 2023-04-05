package com.example.br3;

public class Users {
    public String id,name, email, password, phone, land, city, role;

    public Users() {
    }

    public Users(String id, String name, String email, String password, String phone, String land, String city, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.land = land;
        this.city = city;
        this.role = role;
    }
}
