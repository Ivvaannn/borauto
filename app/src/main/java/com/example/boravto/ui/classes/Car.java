package com.example.boravto.ui.classes;

public class Car {
    public String id, category, idCar, name, img1, img2, img3, description, date, equipment, body, price;

    public Car() {
    }

    public Car(String id, String category, String idCar, String name, String img1, String img2, String img3, String description, String date, String equipment, String body, String price) {
        this.id = id;
        this.category = category;
        this.idCar = idCar;
        this.name = name;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.description = description;
        this.date = date;
        this.equipment = equipment;
        this.body = body;
        this.price = price;
    }
}
