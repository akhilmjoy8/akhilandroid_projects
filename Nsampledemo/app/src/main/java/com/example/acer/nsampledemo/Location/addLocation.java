package com.example.acer.currentlocation.Location;

public class addLocation {

    private  String Name;
    private  String Category;
    private  String Description;
    private  int Rate;
    private  String photo;
    private  String lat;
    private  String lng;

    public addLocation(String name, String category, String description, int rate, String photo, String lat, String lng) {
        Name = name;
        Category = category;
        Description = description;
        Rate = rate;
        this.photo = photo;
        this.lat = lat;
        this.lng = lng;
    }

    public void setRate(int rate) {
        Rate = rate;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getRate() {
        return Rate;
    }

    public void setTitle(int rate) {
        Rate = rate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
