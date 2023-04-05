package com.example.br3;

public class Order {
    public String id, nameCar, idOrder, idUser, idCar, date, address, price, status;

    public Order() {
    }

    public Order(String id, String nameCar, String idOrder, String idUser, String idCar, String date, String address, String price, String status) {
        this.id = id;
        this.nameCar = nameCar;
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.idCar = idCar;
        this.date = date;
        this.address = address;
        this.price = price;
        this.status = status;
    }
}
