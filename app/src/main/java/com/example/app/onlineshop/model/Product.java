package com.example.app.onlineshop.model;

import java.io.Serializable;

public class Product implements Serializable {
    public int id;
    public String name_product;
    public String image_product;
    public int price;
    public String description;
    public int id_product;

    public Product(int id, String name_product, String image_product, int price, String description, int id_product) {
        this.id = id;
        this.name_product = name_product;
        this.image_product = image_product;
        this.price = price;
        this.description = description;
        this.id_product = id_product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getImage_product() {
        return image_product;
    }

    public void setImage_product(String image_product) {
        this.image_product = image_product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }
}
