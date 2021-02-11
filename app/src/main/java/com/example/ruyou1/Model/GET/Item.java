package com.example.ruyou1.Model.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item{
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("target")
    @Expose
    public String target;

    @Override
    public String toString() {
        return "id:" + id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
