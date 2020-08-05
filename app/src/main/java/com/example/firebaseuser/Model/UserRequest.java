package com.example.firebaseuser.Model;

public class UserRequest {
    String name;
    String imgUrl;
    String status;
    String uid;
    String allergy;
    public UserRequest() {
    }

    public UserRequest(String name, String imgUrl, String status,String allergy, String uid) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.status = status;
        this.allergy = allergy;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }
}
