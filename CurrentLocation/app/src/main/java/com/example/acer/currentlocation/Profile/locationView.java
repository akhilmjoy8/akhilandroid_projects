package com.example.acer.currentlocation.Profile;

public class locationView {

    private  String uname;
    private  String upic;
    private  String name;
    private  String desc;
    private  String photo;
    private  String rate;
    private  String lat;
    private  String lng;
    private  String phone;


    public locationView(String uname, String upic, String name, String desc, String photo, String rate, String lat, String lng, String phone) {
        this.uname = uname;
        this.upic = upic;
        this.name = name;
        this.desc = desc;
        this.photo = photo;
        this.rate = rate;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpic() {
        return upic;
    }

    public void setUpic(String upic) {
        this.upic = upic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
