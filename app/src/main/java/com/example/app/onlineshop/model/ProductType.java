package com.example.app.onlineshop.model;

public class ProductType {

    public int id;
    public String nameProductType;
    public String imageType;

    public ProductType(int id, String nameProductType, String imageType) {
        this.id = id;
        this.nameProductType = nameProductType;
        this.imageType = imageType;
    }

    public ProductType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProductType() {
        return nameProductType;
    }

    public void setNameProductType(String nameProductType) {
        this.nameProductType = nameProductType;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
