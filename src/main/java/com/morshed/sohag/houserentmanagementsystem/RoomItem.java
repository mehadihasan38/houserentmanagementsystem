package com.morshed.sohag.houserentmanagementsystem;

public class RoomItem {
    public RoomItem(){}
    private String uid,gender,people,roomnumber,description,imageuri,location;

    public RoomItem(String uid, String gender, String people, String roomnumber, String description, String imageuri, String location) {
        this.uid = uid;
        this.gender = gender;
        this.people = people;
        this.roomnumber = roomnumber;
        this.description = description;
        this.imageuri = imageuri;
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
