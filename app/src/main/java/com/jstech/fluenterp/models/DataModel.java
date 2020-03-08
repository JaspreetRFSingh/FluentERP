package com.jstech.fluenterp.models;

public class DataModel {

    private final String text;
    private final int drawable;
    private final String color;

    public DataModel(String text, int drawable, String color) {
        this.text = text;
        this.drawable = drawable;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getColor() {
        return color;
    }
}
