package com.example.donation;

public class ModelNews {

    String topic;
    String detail;
    String date;
    String uploader;

    ModelNews () { }

    public ModelNews(String topic, String detail, String date, String uploader){
        this.topic = topic;
        this.detail = detail;
        this.date = date;
        this.uploader = uploader;
    }
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }



}
