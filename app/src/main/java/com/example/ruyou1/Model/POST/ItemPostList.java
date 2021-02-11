package com.example.ruyou1.Model.POST;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemPostList {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("contact")
    @Expose
    public List<Contact> contact = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ItemPostList{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", contact=" + contact +
                '}';
    }

    public List<Contact> getContact() {
        return contact;
    }

    public void setContact(String name, String Surname, String patronymic) {
        this.contact = contact;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getImage() {
        return image;
    }
}