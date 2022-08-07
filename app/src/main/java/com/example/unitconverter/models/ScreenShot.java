package com.example.unitconverter.models;

public class ScreenShot {
    String id;
    String pic;
    String time;

    public ScreenShot() {
    }

    public ScreenShot(String id, String pic, String time) {
        this.id = id;
        this.pic = pic;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
