package com.example.app.onlineshop.model;

public class CartProduct {
    public int id;
    public String name;
    public long price;
    public String img;
    public int totalPro;

    public CartProduct(int id, String name, long price, String img, int totalPro) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
        this.totalPro = totalPro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getTotalPro() {
        return totalPro;
    }

    public void setTotalPro(int totalPro) {
        this.totalPro = totalPro;
    }
}
