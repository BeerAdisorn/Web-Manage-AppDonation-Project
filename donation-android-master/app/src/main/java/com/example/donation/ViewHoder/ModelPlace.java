package com.example.donation.ViewHoder;

public class ModelPlace {

    String name;
    String address;
    String phonenumber;
    String image;
    Boolean status;
    Double averageRating;
    Integer countRating;

    public ModelPlace() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getCountRating() {
        return countRating;
    }

    public void setCountRating(Integer countRating) {
        this.countRating = countRating;
    }
}
