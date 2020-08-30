package com.fmm.uberprototype;

public class CarUberItem {
    private int imageId;
    private String name;
    private String value;

    public CarUberItem(int imageId, String name, String value) {
        this.imageId = imageId;
        this.name = name;
        this.value = value;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
