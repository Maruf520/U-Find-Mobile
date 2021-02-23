package com.app.findme;

import android.graphics.Bitmap;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadFound {

    @JsonProperty("image")
    Bitmap image;
    @JsonProperty("phone")
    String phone;
    @JsonProperty("description")
    String description;
    @JsonProperty("location")
    String location;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
