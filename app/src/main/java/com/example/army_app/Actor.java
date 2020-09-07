package com.example.army_app;

import java.math.BigInteger;

public class Actor {
    BigInteger id;
    String name ;
    String image_path;
    public Actor(BigInteger id , String name , String image_path){
        this.id = id;
        this.name = name;
        this.image_path = image_path;
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setName(String name) {
        this.name = name;
    }

}
