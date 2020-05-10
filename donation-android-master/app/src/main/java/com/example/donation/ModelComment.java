package com.example.donation;

public class ModelComment {

    public String nameuser;
    public String textcomment;
    public int rating;
    public String uid;


    public ModelComment() {
    }

    public ModelComment(String nameuser, String textcomment, int rating) {
        this.nameuser = nameuser;
        this.textcomment = textcomment;
        this.rating = rating;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getTextcomment() {
        return textcomment;
    }

    public void setTextcomment(String textcomment) {
        this.textcomment = textcomment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
