package com.example.donation.ModelClasses;

public class UserRegister {

    //    String userId;
    String email;
    String password;
    String firstname;
    String lastname;
    String phone;
    String address;
    String birthday;
    String Identification;

    public UserRegister(String identification) {
        Identification = identification;
    }

    String type;

    public UserRegister() {
    }

    public UserRegister(String userId, String email, String password, String firstname, String lastname, String phone, String address, String Identification,String birthday, String type) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.Identification = Identification;
        this.type = type;
    }

//    public String getUserId() {
//        return userId;
//    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getType() {
        return type;
    }

    public String getIdentification() {
        return Identification;
    }

    public void setIdentification(String identification) {
        Identification = identification;
    }
}