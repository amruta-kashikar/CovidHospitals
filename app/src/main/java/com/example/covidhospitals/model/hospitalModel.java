package com.example.covidhospitals.model;

public class hospitalModel
{
    String name;
    String phone;
    String email;
    String total;
    int vacant;
    String id;

    public hospitalModel(int vacant) {
        this.vacant=vacant;
    }

    public hospitalModel(String name, String phone, String email, String total, int vacant) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.total = total;
        this.vacant = vacant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getVacant() {
        return vacant;
    }

    public void setVacant(int vacant) {
        this.vacant = vacant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
