package com.example.e_carservices.customer;

public class orderdetailsmodel {
    String Price, Name;

    public orderdetailsmodel(String price, String name) {
        Price = price;
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
