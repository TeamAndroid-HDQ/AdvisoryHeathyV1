package com.example.firebaseuser.ViewModel;

public class ItemViewModel {
    String name;
    String content;
    int imgID;

    public ItemViewModel(String name, String content, int imgID) {
        this.name = name;
        this.content = content;
        this.imgID = imgID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }
}
