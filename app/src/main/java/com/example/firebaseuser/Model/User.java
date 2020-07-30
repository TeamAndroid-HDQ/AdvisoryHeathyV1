package com.example.firebaseuser.Model;

public class User {
    String UID, User_name, Email, Phone_number, Role, imgURL, Allergy, Status;

    public User(String UID, String user_name, String email, String phone_number, String role, String imgURL, String allergy, String status) {
        this.UID = UID;
        User_name = user_name;
        Email = email;
        Phone_number = phone_number;
        Role = role;
        this.imgURL = imgURL;
        Allergy = allergy;
        Status = status;
    }

    public User() {
    }
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getAllergy() {
        return Allergy;
    }

    public void setAllergy(String allergy) {
        Allergy = allergy;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


}
