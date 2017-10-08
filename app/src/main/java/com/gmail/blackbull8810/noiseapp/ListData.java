package com.gmail.blackbull8810.noiseapp;

import android.graphics.drawable.Drawable;

public class ListData {
    public int id;

    public String ListValue;

    public String ListTime;

    public String ListValueKorea;

    public Drawable ListImg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListValue() {
        return ListValue;
    }

    public void setListValue(String listValue) {
        ListValue = listValue;
    }

    public String getListTime() {
        return ListTime;
    }

    public void setListTime(String listTime) {
        ListTime = listTime;
    }

    public String getListValueKorea() {
        return ListValueKorea;
    }

    public void setListValueKorea(String listValueKorea) {
        ListValueKorea = listValueKorea;
    }

    public Drawable getListImg() {
        return ListImg;
    }

    public void setListImg(Drawable listImg) {
        ListImg = listImg;
    }
}
