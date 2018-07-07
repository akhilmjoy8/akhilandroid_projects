package com.example.acer.currentlocation.Profile;

public class addProfile {
    private  String name;
    private  String email;
    private  String profilepic;
    private  String state;
    private  String country;
    private  String Phone;

    public addProfile(String name, String email, String profilepic, String state, String country, String Phone) {
        this.name = name;
        this.email = email;
        this.profilepic = profilepic;
        this.state = state;
        this.country = country;
        this.Phone =Phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
