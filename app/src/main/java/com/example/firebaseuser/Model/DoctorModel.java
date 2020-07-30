package com.example.firebaseuser.Model;

public class DoctorModel {
    String doctorName;
    String email;
    String UID;
    String phone_number;
    String specialist;
    String license;
    String imgDoctor;
    String work_place;

    public DoctorModel() {
    }

    public DoctorModel(String UID, String doctorName, String email, String imgDoctor,String phone_number, String specialist, String license, String work_place) {
        this.doctorName = doctorName;
        this.email = email;
        this.UID = UID;
        this.specialist = specialist;
        this.license = license;
        this.imgDoctor = imgDoctor;
        this.work_place = work_place;
        this.phone_number = phone_number;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getImgDoctor() {
        return imgDoctor;
    }

    public void setImgDoctor(String imgDoctor) {
        this.imgDoctor = imgDoctor;
    }

    public String getWork_place() {
        return work_place;
    }

    public void setWork_place(String work_place) {
        this.work_place = work_place;
    }
    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
