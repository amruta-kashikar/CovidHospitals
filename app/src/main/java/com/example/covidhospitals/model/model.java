package com.example.covidhospitals.model;

import com.google.firebase.firestore.PropertyName;

public class model {
    String name,age,gender,condition,time,phone;
    public model(){

    }

    public model(String name, String age, String gender,String condition,String time,String phone) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.condition = condition;
        this.time = time;
        this.phone = phone;
    }
    //@PropertyName("Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //@PropertyName("Age")
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    //@PropertyName("Gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
