package com.example.sneakerstore.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String Gender;
    private String DOB;
    private int point;
    private String password;
    private String Role;
    private String Email;
    private String image;

    public User(int id, String name, String address, String phone, String gender, String DOB, int point, String password, String role, String email, String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        Gender = gender;
        this.DOB = DOB;
        this.point = point;
        this.password = password;
        Role = role;
        Email = email;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
