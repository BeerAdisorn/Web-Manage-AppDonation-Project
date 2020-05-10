package com.example.donation;

public class ModelMap {

    String name;
    String address;
    String phonenumber;
    String category;
    Double latitude;
    Double longitude;

    ModelMap () {

    }

    public ModelMap(String name, String address, String phonenumber, Double latitude, Double longitude, String category) {
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
