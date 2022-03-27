package com.example.e_carservices.customer;

public class cardmodel {
    String title, price;
    String image;
    String id;
    String disp;

    public cardmodel(String title, String price, String image, String id, String disp) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.id = id;
        this.disp = disp;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisp() {
        return disp;
    }

    public void setDisp(String disp) {
        this.disp = disp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
