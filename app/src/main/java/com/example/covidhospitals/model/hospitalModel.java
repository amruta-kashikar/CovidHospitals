package com.example.covidhospitals.model;

public class hospitalModel
{
    String name,area,phone,email,password,total,id;
    int vacant,o2,nonO2,icu,ventilator;

    public hospitalModel(){

    }

    public hospitalModel(int vacant,int o2,int nonO2,int icu,int ventilator) {
        this.vacant = vacant;
        this.o2 = o2;
        this.nonO2 = nonO2;
        this.icu = icu;
        this.ventilator = ventilator;
    }


    public hospitalModel(String name, String area,String phone, String email, String password,String total, String id, int vacant,int o2,int nonO2,int icu,int ventilator) {
        this.name = name;
        this.area = area;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.total = total;
        this.id = id;
        this.vacant = vacant;
        this.o2 = o2;
        this.nonO2 = nonO2;
        this.icu = icu;
        this.ventilator = ventilator;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public int getO2() {
        return o2;
    }

    public void setO2(int o2) {
        this.o2 = o2;
    }

    public int getNonO2() {
        return nonO2;
    }

    public void setNonO2(int nonO2) {
        this.nonO2 = nonO2;
    }

    public int getIcu() {
        return icu;
    }

    public void setIcu(int icu) {
        this.icu = icu;
    }

    public int getVentilator() {
        return ventilator;
    }

    public void setVentilator(int ventilator) {
        this.ventilator = ventilator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
